package com.example.desafio1_appvader

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.launch
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragActivarCuentaBinding
import com.example.desafio1_appvader.databinding.FragmentFragPerfilBinding
import com.example.desafio1_appvader.modelo.UsuarioPerfil
import java.io.File
import java.io.FileOutputStream

class FragPerfil : Fragment() {
    private var _binding: FragmentFragPerfilBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragPerfilViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var bitmap : Bitmap
    private val cameraRequest = 1888
    var uriImagen : Uri? = null
    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            uriImagen = uri
            binding.ivPerfilPerfil.setImageURI(uri)
        }
    }
    val openCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            this.bitmap = bitmap
            binding.ivPerfilPerfil.setImageBitmap(bitmap)
        }
    }
    val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openCamera.launch()
        }
    }
    companion object {
        fun newInstance() = FragPerfil()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mainViewModel.obtenerUsuarioPorIdVM(mainViewModel.usuarioLogeado.value!!)

        mainViewModel.usuario.observe(viewLifecycleOwner){ usuario ->
            if (usuario != null) {
                binding.etUsuarioPerfil.setText(usuario.nombre)
                binding.etEdadPerfil.setText(usuario.edad.toString())
                binding.etPaswordPerfil.setText(usuario.password)
                mainViewModel.obtenerRolPorIdVM(usuario.id)
                mainViewModel.obtenerNivelPorIdVM(usuario.id)
                Glide.with(requireContext()).load(usuario.foto).into(binding.ivPerfilPerfil)
            }
        }
        mainViewModel.cadena.observe(viewLifecycleOwner){cadena ->
            if (cadena != null) {
                binding.etRolPerfil.setText(cadena)
            }
        }
        mainViewModel.cadena2.observe(viewLifecycleOwner){cadena ->
            if (cadena != null) {
                binding.etNivelPerfil.setText(cadena)
            }
        }

        mainViewModel.urlfoto.observe(viewLifecycleOwner){ urlFoto ->
            if (urlFoto != null){
                mainViewModel.modificarPerfilUsuarioVM(UsuarioPerfil(mainViewModel.usuarioLogeado.value!!, binding.etUsuarioPerfil.text.toString(), binding.etPaswordPerfil.text.toString(), urlFoto.replace("http://", "https://")))
                mainViewModel.restablecerUrlFoto()
            }
        }
        mainViewModel.errorCode.observe(viewLifecycleOwner){ error ->
            if (error != null) {
                when (error) {
                    409 -> Toast.makeText(requireContext(),"El nombre de usuario ya existe",Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(requireContext(), "Error al alcualizar los datos", Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),"Datos actualizados", Toast.LENGTH_SHORT).show()
                }
                mainViewModel.restablecerError()
            }
        }

        mainViewModel.resOperacion.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                binding.btnAceptarPerfil.visibility = View.INVISIBLE
                binding.btnCancelarPerfil.visibility = View.INVISIBLE
                binding.btnAbrirCamaraPerfil.visibility = View.INVISIBLE
                binding.btnAbrirGaleriaPerfil.visibility = View.INVISIBLE
                binding.btnEditarPerfil.visibility = View.VISIBLE
                binding.etUsuarioPerfil.isEnabled = false
                binding.etPaswordPerfil.isEnabled = false
                mainViewModel.restablecerResOperacion2()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnAbrirCamaraPerfil.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        binding.btnAbrirGaleriaPerfil.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnAceptarPerfil.setOnClickListener {
            if (binding.etUsuarioPerfil.text.isNullOrBlank() || binding.etPaswordPerfil.text.isNullOrBlank()|| binding.ivPerfilPerfil.drawable == null ){
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }else {
                val bitmap2 = (binding.ivPerfilPerfil.drawable as BitmapDrawable).bitmap
                val file = File(requireContext().cacheDir, "temp_image.jpg")
                val outputStream = FileOutputStream(file)
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                mainViewModel.subirImgen(file)
            }
        }
        binding.btnEditarPerfil.setOnClickListener {
            binding.btnAceptarPerfil.visibility = View.VISIBLE
            binding.btnCancelarPerfil.visibility = View.VISIBLE
            binding.btnAbrirCamaraPerfil.visibility = View.VISIBLE
            binding.btnAbrirGaleriaPerfil.visibility = View.VISIBLE
            binding.btnEditarPerfil.visibility = View.INVISIBLE
            binding.etUsuarioPerfil.isEnabled = true
            binding.etPaswordPerfil.isEnabled = true
        }
        binding.btnCancelarPerfil.setOnClickListener {
            binding.btnAceptarPerfil.visibility = View.INVISIBLE
            binding.btnCancelarPerfil.visibility = View.INVISIBLE
            binding.btnAbrirCamaraPerfil.visibility = View.INVISIBLE
            binding.btnAbrirGaleriaPerfil.visibility = View.INVISIBLE
            binding.btnEditarPerfil.visibility = View.VISIBLE
            binding.etUsuarioPerfil.isEnabled = false
            binding.etPaswordPerfil.isEnabled = false
        }
        binding.btnEliminarPerfil.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("No puedes escapar")
                .setMessage("¿De veras pensabas que ibas a desertar del imperio tan facilmente?")
                .setNeutralButton("Señor, si señor", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int -> })).show()
        }

    }
}
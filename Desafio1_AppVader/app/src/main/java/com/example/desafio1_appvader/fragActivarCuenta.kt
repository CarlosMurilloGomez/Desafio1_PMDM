package com.example.desafio1_appvader


import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cloudinary.Cloudinary
import com.cloudinary.android.MediaManager
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragActivarCuentaBinding
import com.example.desafio1_appvader.modelo.UsuarioPerfil
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class fragActivarCuenta : Fragment() {
    private var _binding: FragmentFragActivarCuentaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragActivarCuentaViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var bitmap : Bitmap
    private val cameraRequest = 1888
    var uriImagen : Uri? = null
    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            uriImagen = uri
            binding.ivPerfilActivar.setImageURI(uri)
        }
    }
    val openCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            this.bitmap = bitmap
            binding.ivPerfilActivar.setImageBitmap(bitmap)
        }
    }
    val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openCamera.launch()
        }
    }
    companion object {
        fun newInstance() = fragActivarCuenta()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragActivarCuentaBinding.inflate(inflater, container, false)
        val root: View = binding.root


        mainViewModel.cadena.observe(viewLifecycleOwner){ urlFoto ->
            if (urlFoto != null){
                mainViewModel.modificarPerfilUsuarioVM(UsuarioPerfil(mainViewModel.usuarioLogeado.value!!, binding.etUsuarioActivar.text.toString(), binding.etPaswordActivar.text.toString(), urlFoto))
                mainViewModel.restablecerCadena()
            }
        }
        mainViewModel.errorCode.observe(viewLifecycleOwner){ error ->
            if (error != null) {
                when (error) {
                    409 -> Toast.makeText(requireContext(),"El nombre de usuario ya existe",Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(requireContext(), "Error 400", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(requireContext(),"Error desconocido" + error, Toast.LENGTH_SHORT).show()
                }
                mainViewModel.restablecerError()
            }
        }
        mainViewModel.resOperacion.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                mainViewModel.activarCuentaVM(mainViewModel.usuarioLogeado.value!!)
                mainViewModel.restablecerResOperacion()
            }
        }
        mainViewModel.resOperacion2.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                Navigation.findNavController(requireView()).popBackStack(R.id.fragLogin, false)
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



        binding.btnAbrirCamaraActivar.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        binding.btnAbrirGaleriaActivar.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnActivarActivar.setOnClickListener {
            if (binding.etUsuarioActivar.text.isNullOrBlank() || binding.etPaswordActivar.text.isNullOrBlank() || binding.etConfPaswordActivar.text.isNullOrBlank() || binding.ivPerfilActivar.drawable == null ){
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }else if(binding.etPaswordActivar.text.toString() != binding.etConfPaswordActivar.text.toString()){
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }else {
                val bitmap2 = (binding.ivPerfilActivar.drawable as BitmapDrawable).bitmap
                val file = File(requireContext().cacheDir, "temp_image.jpg")
                val outputStream = FileOutputStream(file)
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                mainViewModel.subirImgen(file)
            }
        }
        binding.btnCancelarActivar.setOnClickListener {
            Navigation.findNavController(view).popBackStack(R.id.fragLogin, false)
        }

    }
}
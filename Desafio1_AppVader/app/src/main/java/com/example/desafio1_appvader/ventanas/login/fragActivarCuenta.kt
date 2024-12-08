package com.example.desafio1_appvader.ventanas.login


import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
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
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.api.MainViewModel
import com.example.desafio1_appvader.databinding.FragmentFragActivarCuentaBinding
import com.example.desafio1_appvader.modelo.usuario.UsuarioPerfil
import java.io.File
import java.io.FileOutputStream


class fragActivarCuenta : Fragment() {
    private var _binding: FragmentFragActivarCuentaBinding? = null
    private val binding get() = _binding!!
    private val fragActivarCuentaViewModel: FragActivarCuentaViewModel by viewModels()
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


        fragActivarCuentaViewModel.urlfoto.observe(viewLifecycleOwner){ urlFoto ->
            if (urlFoto != null){
                fragActivarCuentaViewModel.modificarPerfilUsuarioVM(UsuarioPerfil(mainViewModel.usuarioLogeado.value!!.id, binding.etPaswordActivar.text.toString(), urlFoto.replace("http://", "https://")))
                fragActivarCuentaViewModel.restablecerUrlFoto()
            }
        }
        fragActivarCuentaViewModel.errorCode.observe(viewLifecycleOwner){ error ->
            if (error != null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), getString(R.string.errActivar), Toast.LENGTH_SHORT).show()
                    202 -> Toast.makeText(requireContext(),getString(R.string.msjPerfilActualizado), Toast.LENGTH_SHORT).show()
                }
                fragActivarCuentaViewModel.restablecerError()
            }
        }
        fragActivarCuentaViewModel.resModificar.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                fragActivarCuentaViewModel.activarCuentaVM(mainViewModel.usuarioLogeado.value!!.id)
                fragActivarCuentaViewModel.restablecerResModificar()
            }
        }
        fragActivarCuentaViewModel.resActivar.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                mainViewModel.cerrarSesionVM()
                Navigation.findNavController(requireView()).popBackStack(R.id.nav_fragLogin, false)
                fragActivarCuentaViewModel.restablecerResActivar()
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

        binding.etUsuarioActivar.setText(mainViewModel.usuarioLogeado.value!!.nombre)

        binding.btnAbrirCamaraActivar.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        binding.btnAbrirGaleriaActivar.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnActivarActivar.setOnClickListener {
            if (binding.etUsuarioActivar.text.isNullOrBlank() || binding.etPaswordActivar.text.isNullOrBlank() || binding.etConfPaswordActivar.text.isNullOrBlank() ){
                Toast.makeText(requireContext(), resources.getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
            }else if(binding.etPaswordActivar.text.toString() != binding.etConfPaswordActivar.text.toString()){
                Toast.makeText(requireContext(), resources.getString(R.string.errPassword), Toast.LENGTH_SHORT).show()
            }else if (binding.ivPerfilActivar.drawable == null) {
                fragActivarCuentaViewModel.modificarPerfilUsuarioVM(UsuarioPerfil(mainViewModel.usuarioLogeado.value!!.id, binding.etPaswordActivar.text.toString(), mainViewModel.usuarioLogeado.value!!.foto))
            }else{
                val bitmap2 = (binding.ivPerfilActivar.drawable as BitmapDrawable).bitmap
                val file = File(requireContext().cacheDir, "temp_image.jpg")
                val outputStream = FileOutputStream(file)
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                fragActivarCuentaViewModel.subirImgen(file)
            }
        }
        binding.btnCancelarActivar.setOnClickListener {
            Navigation.findNavController(view).popBackStack(R.id.nav_fragLogin, false)
        }

    }
}
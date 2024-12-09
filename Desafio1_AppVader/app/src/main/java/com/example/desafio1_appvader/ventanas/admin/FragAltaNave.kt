package com.example.desafio1_appvader.ventanas.admin

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.desafio1_appvader.R
import com.example.desafio1_appvader.databinding.FragmentFragAltaNaveBinding
import com.example.desafio1_appvader.databinding.FragmentFragAltaPilotoBinding
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.usuario.Usuario
import com.example.desafio1_appvader.modelo.usuario.UsuarioPerfil
import java.io.File
import java.io.FileOutputStream

class FragAltaNave : Fragment() {
    private var _binding: FragmentFragAltaNaveBinding? = null
    private val binding get() = _binding!!
    private val fragAltaNaveViewModel: FragAltaNaveViewModel by viewModels()

    private lateinit var bitmap : Bitmap
    private val cameraRequest = 1888
    var uriImagen : Uri? = null
    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            uriImagen = uri
            binding.ivFotoAltaNa.setImageURI(uri)
        }
    }
    val openCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            this.bitmap = bitmap
            binding.ivFotoAltaNa.setImageBitmap(bitmap)
        }
    }
    val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openCamera.launch()
        }
    }
    companion object {
        fun newInstance() = FragAltaNave()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragAltaNaveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var tipos = ArrayList<Tipo>()
        fragAltaNaveViewModel.obtenerTiposNavesVM()
        fragAltaNaveViewModel.tipos.observe(viewLifecycleOwner){
            for (tipo in it){
                when (tipo.id) {
                    1 -> tipos.add(Tipo(1, getString(R.string.tipoCaza)))
                    2 -> tipos.add(Tipo(2, getString(R.string.tipoBombardero)))
                    3 -> tipos.add(Tipo(3, getString(R.string.tipoNaveTransporte)))
                }
            }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTipoAltaNa.adapter = adapter
        }
        binding.spTipoAltaNa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
                when ((selectedItem as Tipo).id) {
                    1 -> {
                        binding.chBoxCargaAltaNa.isEnabled = false
                        binding.chBoxCargaAltaNa.isChecked = false
                        binding.chBoxPasajerosAltaNa.isChecked = false
                        binding.chBoxPasajerosAltaNa.isEnabled = false
                    }
                    else -> {
                        binding.chBoxCargaAltaNa.isEnabled = true
                        binding.chBoxPasajerosAltaNa.isEnabled = true
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //Acción cuando no se selecciona nada
            }
        }
        fragAltaNaveViewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    400 -> Toast.makeText(requireContext(), getString(R.string.errMatricula), Toast.LENGTH_SHORT).show()
                    201 -> Toast.makeText(requireContext(), getString(R.string.msjNaveRegistrada), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errRegistrar), Toast.LENGTH_SHORT).show()
                }
            }
        }
        fragAltaNaveViewModel.urlfoto.observe(viewLifecycleOwner){ urlFoto ->
            if (urlFoto != null){
                registrarNave(urlFoto.replace("http://", "https://"))
                fragAltaNaveViewModel.restablecerUrlFoto()
            }
        }
        fragAltaNaveViewModel.resRegistro.observe(viewLifecycleOwner){
            if (it!=null && it==true) {
                Navigation.findNavController(requireView()).navigate(R.id.nav_fragBajaNave)
                fragAltaNaveViewModel.restablecerResRegistro()
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
        binding.btnAbrirCamaraAltaNa.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        binding.btnAbrirGaleriaAltaNa.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnRegistrarAltaNa.setOnClickListener {
            if (binding.etMatriculaAltaNa.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
            }else {
                if (binding.ivFotoAltaNa.drawable == null){
                    registrarNave("")
                }else{
                    val bitmap2 = (binding.ivFotoAltaNa.drawable as BitmapDrawable).bitmap
                    val file = File(requireContext().cacheDir, "temp_image.jpg")
                    val outputStream = FileOutputStream(file)
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()

                    fragAltaNaveViewModel.subirImgen(file)
                }
            }
        }
        binding.btnCancelarAltaNa.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragBajaNave)
        }

    }

    fun registrarNave(urlFoto : String){
        var carga = 0
        var pasajeros = 0
        var foto = urlFoto
        if (binding.chBoxCargaAltaNa.isChecked){
            carga= 1
        }
        if (binding.chBoxPasajerosAltaNa.isChecked){
            pasajeros = 1
        }
        if (urlFoto.isEmpty()){
            when ((binding.spTipoAltaNa.selectedItem as Tipo).id) {
                1 -> foto = "https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342951/TIE_fighter_u8fwd8.png"
                2 -> foto = "https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/TIE_Bomber_zzqmlf.png"
                3 -> foto = "https://res.cloudinary.com/dxqrclhjs/image/upload/v1733342950/shuttle_pi8nyj.jpg"
            }
        }

        fragAltaNaveViewModel.registrarNaveVM(Nave(binding.etMatriculaAltaNa.text.toString().trim(), foto, (binding.spTipoAltaNa.selectedItem as Tipo).id, carga, pasajeros))



    }
}
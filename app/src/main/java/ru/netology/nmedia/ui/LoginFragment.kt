package ru.netology.nmedia.ui



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.databinding.LoginFragmentBinding
import ru.netology.nmedia.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = LoginFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        val login = binding.username.text
        val pass = binding.password.text

    binding.enter.setOnClickListener {

            viewModel.onLogin(login,pass)
        
            Toast.makeText(context, "данные отправлены:  " + login +"  " + pass, Toast.LENGTH_SHORT).show()
        val fm: FragmentManager = requireActivity().supportFragmentManager
        fm.popBackStack()
        }
    Toast.makeText(context, "введите логин и пароль", Toast.LENGTH_SHORT).show()
        return binding.root
    }


}


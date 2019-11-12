package buu.informatics.s59160581.trafficsign


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import buu.informatics.s59160581.trafficsign.databinding.FragmentHomeBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)
        binding.playBtn.setOnClickListener { View ->
            View.findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
            Toast.makeText(activity, "You clicked to Play Game", Toast.LENGTH_SHORT).show()
        }
        binding.warningBtn.setOnClickListener { View ->
            View.findNavController().navigate(R.id.action_homeFragment_to_warningFragment)
            Toast.makeText(activity, "You clicked to view Warining Sign", Toast.LENGTH_SHORT).show()
        }
        binding.stopBtn.setOnClickListener { View ->
            View.findNavController().navigate(R.id.action_homeFragment_to_stopFragment)
            Toast.makeText(activity, "You clicked to view Stop sign", Toast.LENGTH_SHORT).show()
        }
        Timber.i("HomeFragment")
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}

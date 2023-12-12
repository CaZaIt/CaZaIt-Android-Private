package org.cazait.ui.cafeinfo

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import org.cazait.R
import org.cazait.databinding.DialogFragmentCafeMapBinding
import org.cazait.core.model.cafe.Cafe

open class CafeInfoDialogFragment(
    private val cafe: Cafe,
    private val navEvent: (() -> (Unit))? = null,
    private val cancelEvent: (() -> (Unit))? = null,
) : DialogFragment() {
    private var _binding: DialogFragmentCafeMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment_cafe_map,
            null,
            false,
        )
        binding.item = cafe

        val dialog = AlertDialog.Builder(requireActivity(), R.style.DialogTheme)
            .setView(binding.root)
            .create()

        binding.ivCancel.setOnClickListener {
            dialog.cancel()
        }
        binding.tvState.setOnClickListener {
            dialog.cancel()
            navEvent?.invoke()
        }

        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        return dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancelEvent?.invoke()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

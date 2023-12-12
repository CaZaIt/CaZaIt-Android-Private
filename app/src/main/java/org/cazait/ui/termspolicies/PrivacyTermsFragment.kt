package org.cazait.ui.termspolicies

import android.content.Context
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentPrivacyTermsBinding
import org.cazait.ui.base.BaseFragment
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@AndroidEntryPoint
class PrivacyTermsFragment : BaseFragment<FragmentPrivacyTermsBinding, PrivacyTermsViewModel>(
    PrivacyTermsViewModel::class.java,
    R.layout.fragment_privacy_terms,
) {
    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.terms_privacy)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        // termslocation.txt 파일을 읽어와서 txt_location TextView에 설정
        val termsText = readTextFile(requireContext(), R.raw.termsprivacy)
        binding.txtPrivacy.text = termsText // txt_location 아이디의 TextView에 설정
    }

    override fun initAfterBinding() {
    }

    private fun readTextFile(context: Context, resourceId: Int): String {
        val text = StringBuilder()

        try {
            val inputStream: InputStream = context.resources.openRawResource(resourceId)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                text.append(line).append('\n')
            }

            reader.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return text.toString()
    }
}

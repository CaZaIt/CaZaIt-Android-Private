package org.cazait.ui.announcement

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentAnnouncementBinding
import org.cazait.model.Announcement
import org.cazait.ui.adapter.AnnouncementAdapter
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class AnnouncementFragment: BaseFragment<FragmentAnnouncementBinding, AnnouncementViewModel>(
    AnnouncementViewModel::class.java,
    R.layout.fragment_announcement,
) {

    private lateinit var adapter: AnnouncementAdapter

    override fun initView() {
        binding.clTop.includedTvTitle.text = getString(R.string.see_more_verified)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        // 데이터 리스트 생성 (가상의 데이터)
        val announcements = listOf(
            Announcement("카자잇 어플 서비스 시작!!!", "카자잇 어플에 가입해주셔서 감사합니다!!♡\n\n꾸준히 좋은 서비스를 제공할 수 있는 카자잇이 되겠습니다!! ", "2023.07.26"),
            Announcement("공지사항", "공지사항 내용 공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용공지사항 내용", "2023.07.25"),
            // Add more announcements as needed
        )

        // 어댑터 초기화 및 리사이클러뷰에 설정
        adapter = AnnouncementAdapter(announcements)
        binding.recycleAnnouncement.adapter = adapter
    }

    override fun initAfterBinding() {

    }

}
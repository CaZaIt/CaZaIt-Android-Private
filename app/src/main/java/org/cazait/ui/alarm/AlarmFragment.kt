package org.cazait.ui.alarm

import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.core.model.Alarm
import org.cazait.databinding.FragmentAlarmBinding
import org.cazait.ui.adapter.AlarmAdapter
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding, AlarmViewModel>(
    AlarmViewModel::class.java,
    R.layout.fragment_alarm,
) {
    private lateinit var adapter: AlarmAdapter

    override fun initView() {
        val alarms = listOf(
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            Alarm("알림이 떴습니다!!", "그런데 정확히 무슨 알림이 뜨는 건가요?"),
            // Add more announcements as needed
        )

        // 어댑터 초기화 및 리사이클러뷰에 설정
        adapter = AlarmAdapter(alarms)
        binding.recycleAlarm.adapter = adapter
    }

    override fun initAfterBinding() {
    }
}

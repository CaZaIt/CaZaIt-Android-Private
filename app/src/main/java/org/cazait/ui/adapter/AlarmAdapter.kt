package org.cazait.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.cazait.R
import org.cazait.model.Alarm

class AlarmAdapter(private val items:List<Alarm>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>(){
    // 뷰홀더 클래스 생성
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.txt_alarm_1)
        val detailTextView: TextView = itemView.findViewById(R.id.txt_alarm_2)
    }

    // 아이템 뷰를 위한 레이아웃을 생성하고 뷰홀더 객체를 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return ViewHolder(view)
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.detailTextView.text = item.detail
    }

    // 데이터 개수 반환
    override fun getItemCount(): Int {
        return items.size
    }
}

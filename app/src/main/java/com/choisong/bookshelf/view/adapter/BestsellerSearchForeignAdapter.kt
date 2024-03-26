package com.choisong.bookshelf.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ItemBestsellerSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BestsellerSearchForeignAdapter(private val context: Context, private val onClickListener: OnClickListener): RecyclerView.Adapter<BestsellerSearchForeignAdapter.ViewHolder>() {

    interface OnClickListener{
        fun foreignItemClick(b: Boolean, c: String, n: Int)
    }

    private var bestsellerSearch = MyApplication.prefs.getBestsellerSearch("bestsellerSearch", "")
    private val foreignSearchList = arrayListOf<String>("소설/시", "에세이(외국)", "어린이(외국)", "경제경영(외국)", "인문/사회", "컴퓨터", "자기계발(외국)", "여행(외국)", "건강/스포츠")
    private val foreignSearchMap: Map<String, Int> = mapOf(
        "소설/시" to 90842, "에세이(외국)" to 90845, "어린이(외국)" to 106165, "경제경영(외국)" to 90835, "인문/사회" to 90853, "컴퓨터" to 90859, "자기계발(외국)" to 90854
        , "여행(외국)" to 90846, "건강/스포츠" to 90833)
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBestsellerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foreignSearchList[position])
    }

    override fun getItemCount(): Int {
        return foreignSearchList.size
    }

    inner class ViewHolder(private val binding: ItemBestsellerSearchBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(foreignSearch: String) = with(binding){
            CoroutineScope(Dispatchers.Main.immediate).launch {
                btnSearch.text = foreignSearch
                bestsellerSearch = MyApplication.prefs.getBestsellerSearch("bestsellerSearch", "")
                if (foreignSearch == bestsellerSearch) {
                    btnSearch.isSelected = true
                    btnSearch.setBackgroundResource(R.drawable.bg_green_no_30)
                    btnSearch.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    btnSearch.isSelected = false
                    btnSearch.setBackgroundResource(R.drawable.bg_no_767676_30)
                    btnSearch.setTextColor(ContextCompat.getColor(context, R.color.color767676))
                }

                btnSearch.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        MyApplication.prefs.setBestsellerSearch("bestsellerSearch", btnSearch.text.toString())
                    }

                    if (position != selectedPosition) {
                        selectedPosition = position
                        notifyDataSetChanged() // RecyclerView 업데이트
                    }

                    onClickListener.foreignItemClick(true, "Foreign", foreignSearchMap[btnSearch.text.toString()]!!)
                }
            }
        }
    }
}
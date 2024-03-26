package com.choisong.bookshelf.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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

class BestsellerSearchKoreaAdapter(private val context: Context, private val onClickListener: OnClickListener): RecyclerView.Adapter<BestsellerSearchKoreaAdapter.ViewHolder>() {

    companion object {
        const val TAG = "BestsellerSearchKoreaAdapter"
    }

    interface OnClickListener{
        fun koreaItemClick(b: Boolean, c: String, n: Int)
    }

    private var bestsellerSearch = MyApplication.prefs.getBestsellerSearch("bestsellerSearch", "")
    private val koreaSearchList = arrayListOf<String>("전체 국내 도서", "자기계발", "여행", "소설/시/희곡", "경제경영", "건강/취미/레저", "가정/요리/뷰티", "과학", "만화", "어린이", "에세이"
        , "역사", "예술/대중문화", "외국어", "유아", "인문학", "장르소설", "종교/역학", "컴퓨터/모바일")
    private val koreaSearchMap: Map<String, Int> = mapOf(
        "전체 국내 도서" to 0, "자기계발" to 336, "여행" to 1196, "소설/시/희곡" to 1, "경제경영" to 170, "건강/취미/레저" to 55890, "가정/요리/뷰티" to 1230, "과학" to 987
        , "만화" to 2551, "어린이" to 1108, "에세이" to 55889, "역사" to 74, "예술/대중문화" to 517, "외국어" to 1322, "유아" to 13789, "인문학" to 656, "장르소설" to 112011, "종교/역학" to 1237, "컴퓨터/모바일" to 351)
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBestsellerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(koreaSearchList[position], position)
    }

    override fun getItemCount(): Int {
        return koreaSearchList.size
    }

    inner class ViewHolder(private val binding: ItemBestsellerSearchBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("NotifyDataSetChanged")
        fun bind(koreaSearch: String, position: Int) = with(binding){
            CoroutineScope(Dispatchers.Main.immediate).launch {
                btnSearch.text = koreaSearch
                bestsellerSearch = MyApplication.prefs.getBestsellerSearch("bestsellerSearch", "")
                if (koreaSearch == bestsellerSearch) {
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

                    onClickListener.koreaItemClick(true, "Book", koreaSearchMap[btnSearch.text.toString()]!!)
                }
            }
        }
    }
}
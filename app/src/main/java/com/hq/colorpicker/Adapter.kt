package com.hq.colorpicker

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/** @author Haider Qadir **/

class Adapter(var context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var JSON_STRING = JSONColors().JSONColorString
    var jsonArray = JSONArray(JSON_STRING)

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val linear: LinearLayout = itemView.findViewById(R.id.linear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return jsonArray.length();

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            for (i in 0 until jsonArray.length()) {
                val jsonobject: JSONObject = jsonArray.getJSONObject(position)

                var name = jsonobject.getString("hex");

                holder.textView.setText(name)
                holder.linear.setBackgroundColor(Color.parseColor(name))

                holder.linear.setOnClickListener {
                    val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

//                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                    val clip = ClipData.newPlainText("label", holder.textView.text)
                    clipboard!!.setPrimaryClip(clip)
                }

            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }
//        TODO("Not yet implemented")
    }
}
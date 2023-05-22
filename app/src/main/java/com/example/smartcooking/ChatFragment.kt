package com.example.smartcooking
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartcooking.R
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ChatFragment : Fragment() {
    private val API_KEY = "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"
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeText: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private val client = OkHttpClient()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        messageList = ArrayList()
        recyclerView = view.findViewById(R.id.recycler_view)
        welcomeText = view.findViewById(R.id.welcome_text)
        messageEditText = view.findViewById(R.id.message_edit_text)
        sendButton = view.findViewById(R.id.sent_bt)
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        val promptEngineer = "You are a professional cook. Only answer with relevance to cooking. "
        addToChat(promptEngineer, Message.SENT_BY_ME)

        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim { it <= ' ' }
            addToChat(question, Message.SENT_BY_ME)
            messageEditText.setText("")
            callAPI(question)
            welcomeText.visibility = View.GONE
        }

        return view
    }

    private fun addToChat(message: String, sentBy: String) {
        requireActivity().runOnUiThread {
            messageList.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String?) {
        messageList.removeAt(messageList.size - 1)
        addToChat(response!!, Message.SENT_BY_BOT)
    }

    private fun callAPI(question: String) {
        messageList.add(Message("Cooking...", Message.SENT_BY_BOT))
        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "text-davinci-003")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val body: RequestBody = RequestBody.create(JSON, jsonBody.toString())
        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer $API_KEY")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful || response.code in 200..299) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body?.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        addResponse(result.trim { it <= ' ' })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to ${response.body?.string()}")
                }
            }
        })
    }

    companion object {
        private val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }
}



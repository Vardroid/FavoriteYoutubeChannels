package com.example.favoriteyoutubechannels

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.favoriteyoutubechannels.handlers.ChannelHandler
import com.example.favoriteyoutubechannels.model.TopChannel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var channelList: ListView
    lateinit var channelHandler: ChannelHandler

    companion object{
        lateinit var channels: ArrayList<TopChannel>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        channelHandler = ChannelHandler()
        channelList = findViewById<ListView>(R.id.main_list)
        channels = ArrayList()

        val fab: View = findViewById(R.id.main_add_fbtn)
        fab.setOnClickListener {
            startActivity(Intent(this, AddChannel::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        channelHandler.channelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                channels.clear()
                snapshot.children.forEach {
                        it -> val channel = it.getValue(TopChannel::class.java)
                        channels.add(channel!!)
                }

                val adapter = MainListAdapter(applicationContext, channels)
                channelList.adapter = adapter
                registerForContextMenu(channelList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    class MainListAdapter(context: Context, items: ArrayList<TopChannel>): BaseAdapter(){
        private val mContext: Context = context
        private val mItems: MutableList<TopChannel> = items

        override fun getCount(): Int {
            return mItems.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val itemView = layoutInflater.inflate(R.layout.main_list_item, parent, false)

            val headerView = layoutInflater.inflate(R.layout.main_list_header, parent, false)

            val mainNum = itemView.findViewById<TextView>(R.id.number_txt)
            val mainName = itemView.findViewById<TextView>(R.id.channel_name_txt)
            val mainLink = itemView.findViewById<TextView>(R.id.channel_link_txt)
            val mainReason = itemView.findViewById<TextView>(R.id.channel_reason_txt)

            return if(position == 0){
                headerView
            }else {
                mainNum.text = "$position."
                mainName.text = mItems[position].name
                mainLink.text = mItems[position].link
                mainReason.text = mItems[position].reason

                itemView
            }
        }
    }

    //CONTEXT MENU
    //
    //Create Context Menu when you long press an item in the song list
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater

        //Add the menu items for the context menu
        inflater.inflate(R.menu.main_item_menu, menu)
    }

    //Method when a context item is selected
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.edit_channel -> {
                //get the channel that was selected
                val chanId = channels[info.position].id

                //put it in an extra
                val intent = Intent(applicationContext, EditChannel::class.java)
                intent.putExtra("chanId", chanId)
                intent.putExtra("position", info.position)

                //start activity
                startActivity(intent)
                true
            }
            R.id.delete_channel -> {
                if(channelHandler.delete(channels[info.position])){
                    Toast.makeText(applicationContext, "Channel Deleted.", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
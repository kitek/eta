package pl.kitek.eta.main

import android.support.wearable.view.WearableListView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.eta_list_item.view.*
import pl.kitek.eta.R
import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.ListItem
import pl.kitek.eta.common.data.model.ListItemNew
import pl.kitek.eta.common.data.model.Location
import java.util.*

class MainAdapter() : WearableListView.Adapter() {

    private val items = ArrayList<ListItem>()

    init {
        items.add(ListItemNew())
        items.add(Eta(
                Location("Urocza 15 Elbląg", 54.1698451f, 19.4034976f),
                Location("Schibsted Gdańsk", 54.4029028f, 18.5696496f)
        ))
        items.add(Eta(
                Location("Schibsted Gdańsk", 0f, 0f),
                Location("Schibsted Kraków", 0f, 0f)
        ))
    }

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WearableListView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.eta_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: WearableListView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(items[position])
    }

    fun getItemAtPosition(position: Int): ListItem = items[position]

    class ItemViewHolder(itemView: View) : WearableListView.ViewHolder(itemView) {
        fun bind(item: ListItem) = with(item) {
            itemView.nameView.text = getName()
            val drawable = when (this) {
                is ListItemNew -> R.drawable.ic_add_circle_blue_24dp
                else -> R.drawable.ic_history
            }
            itemView.circleView.setImageResource(drawable)
        }
    }
}


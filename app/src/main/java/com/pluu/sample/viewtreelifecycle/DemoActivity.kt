package com.pluu.sample.viewtreelifecycle

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import java.text.Collator

class DemoActivity : ListActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val path: String? = intent.getStringExtra(samplePath)

        listAdapter = SimpleAdapter(
            this,
            getData(path),
            android.R.layout.simple_list_item_1,
            arrayOf("title"),
            intArrayOf(android.R.id.text1)
        )
        listView.isTextFilterEnabled = true
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        @Suppress("UNCHECKED_CAST")
        val map = l.getItemAtPosition(position) as Map<String, Intent>
        startActivity(map["intent"])
    }

    private fun getData(prefix: String?): List<Map<String, Any>> {
        val prefixPath: Array<String>? = prefix?.split("/".toRegex())?.toTypedArray()
        val prefixWithSlash: String? = if (prefix != null) "$prefix/" else null

        val pm = packageManager

        val entries = HashMap<String, Boolean>()
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(sampleCategory)
            setPackage(applicationContext.packageName)
        }
        val myData = ArrayList<Map<String, Any>>()
        val list = pm.queryIntentActivities(mainIntent, 0) ?: return myData

        list.forEach {
            val label = it.loadLabel(pm)?.toString() ?: it.activityInfo.name

            // If prefixWithSlash is null, we are at the top level so include everything.
            // Otherwise, we are down a level, so only include the activity if it's label starts
            // with our current path.
            if (prefixWithSlash == null || label.startsWith(prefixWithSlash)) {

                val labelPath = label.split("/".toRegex()).toTypedArray()

                // Get the next label in the path for the given activity.  This may be the name of
                // activity itself, or may be a directory.
                val nextLabel = if (prefixPath == null) labelPath[0] else labelPath[prefixPath.size]

                // If the labelPath has one more item in it then the prefixPath does, we are at a
                // leaf node, so we know we are launching an Activity.  Otherwise, we won't be at
                // a leaf node so we are going to open another sub menu.
                if ((prefixPath?.size ?: 0) == labelPath.size - 1) {
                    addItem(
                        myData, nextLabel, activityIntent(
                            it.activityInfo.applicationInfo.packageName,
                            it.activityInfo.name
                        )
                    )
                } else if (entries[nextLabel] == null) {
                    // We only end up here if we are going to launch into another "directory" and if
                    // we haven't already setup an item for that directory.

                    addItem(
                        myData, nextLabel, browseIntent(
                            if (prefix == null) nextLabel else "$prefix/$nextLabel"
                        )
                    )
                    entries[nextLabel] = true
                }
            }
        }

        return myData.sortedWith(sDisplayNameComparator)
    }

    private fun activityIntent(pkg: String, componentName: String): Intent {
        return Intent().apply {
            setClassName(pkg, componentName)
        }
    }

    private fun browseIntent(path: String): Intent {
        return Intent().apply {
            setClass(this@DemoActivity, DemoActivity::class.java)
            putExtra(samplePath, path)
        }
    }

    private fun addItem(data: MutableList<Map<String, Any>>, name: String, intent: Intent) {
        data.add(mutableMapOf("title" to name, "intent" to intent))
    }

    companion object {

        private val sDisplayNameComparator = object : Comparator<Map<String, Any>> {
            private val collator = Collator.getInstance()

            override fun compare(map1: Map<String, Any>, map2: Map<String, Any>): Int {
                return collator.compare(map1["title"], map2["title"])
            }
        }

        const val samplePath = "com.pluu.example"
        const val sampleCategory = "com.pluu.ui.demos.SAMPLE_CODE"
    }
}
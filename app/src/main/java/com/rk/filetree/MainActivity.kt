package com.rk.filetree

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rk.filetree.interfaces.FileClickListener
import com.rk.filetree.interfaces.FileLongClickListener
import com.rk.filetree.interfaces.FileObject
import com.rk.filetree.model.Node
import com.rk.filetree.provider.file
import com.rk.filetree.widget.FileTree

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Initializes the FileTree view. Alternatively, you can define the FileTree widget directly in the
         * activity's XML layout as follows:
         *
         * <com.rk.filetree.widget.FileTree
         *     android:layout_width="match_parent"
         *     android:layout_height="match_parent" />
         *
         * It is also recommended to wrap this view within a HorizontalScrollView or a DiagonalScrollView,
         * DiagonalScrollView is provided by this library, to enable smoother navigation through large file structures.
         */


        // FileTree is a subclass of RecyclerView
        val fileTree = FileTree(this)

        /**
         * To load the file tree view, use the loadFiles method. Before calling this method,
         * ensure that you have created a wrapper for the FileObject interface.
         * @see com.rk.filetree.interfaces.FileObject
         *
         * The FileTree widget provides a simple wrapper for java.io.File objects.
         * @see com.rk.filetree.provider.file
         */

        // Using private app data files as a demo
        val targetFile = filesDir.parentFile!!

        // Creating a FileObject wrapper
        val fileObject = file(targetFile)

        /**
         * The loadFiles method accepts an optional boolean parameter. If set to false,
         * the root node will not be displayed. If true or null, the root node will be shown.
         * To change the root file, simply call the loadFiles method again with a different FileObject.
         */

        // Load the file tree
        fileTree.loadFiles(fileObject)


        // Custom icon provider setup (optional). The FileTree widget uses a default icon provider if none is specified.
        // Uncomment the following section to define a custom icon provider that supplies specific icons
        // for files, directories, and expandable nodes:

        // fileTree.setIconProvider(object : FileIconProvider {
        //     override fun getIcon(node: Node<FileObject>): Drawable? {
        //         val fileObject = node.value
        //         return if (fileObject.isDirectory()) {
        //             directoryDrawable  // Provide a custom drawable for directories
        //         } else {
        //             fileDrawable  // Provide a custom drawable for files
        //         }
        //     }
        //
        //     override fun getChevronRight(): Drawable? {
        //         return chevronDrawable  // Provide a custom drawable for the chevron icon
        //     }
        //
        //     override fun getExpandMore(): Drawable? {
        //         return expandChevronDrawable  // Provide a custom drawable for the expand more icon
        //     }
        // })


        // Setting a file click listener
        fileTree.setOnFileClickListener(object : FileClickListener {
            override fun onClick(node: Node<FileObject>) {
                val fileObject = node.value
                Toast.makeText(
                    this@MainActivity,
                    "Clicked: ${fileObject.getName()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        // Setting a file long-click listener
        fileTree.setOnFileLongClickListener(object : FileLongClickListener {
            override fun onLongClick(node: Node<FileObject>) {
                val fileObject = node.value
                Toast.makeText(
                    this@MainActivity,
                    "Long clicked: ${fileObject.getName()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        // You can also refresh the file tree by calling the reloadFileTree method.
        // fileTree.reloadFileTree()


        setContentView(fileTree)
    }
}

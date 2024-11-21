package ano.onescript.node

import android.app.Application
import android.content.res.AssetManager
import android.util.Log
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        copyNodeProject()
    }

    fun copyNodeProject() {
        val nodeProjectDir = File(this.filesDir, "node-project")
        if (nodeProjectDir.exists()) {
            nodeProjectDir.deleteRecursively()
        }

        val assetManager = this.assets
        assetManager.copyAssetFolder("node-project", nodeProjectDir.absolutePath)

        Log.d("CustomApp", "Copied node-project")
    }
}

fun AssetManager.copyAssetFile(srcName: String, dstName: String): Boolean {
    return try {
        val inStream = this.open(srcName)
        val outFile = File(dstName)
        val out: OutputStream = FileOutputStream(outFile)
        val buffer = ByteArray(1024)
        var read: Int
        while (inStream.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
        inStream.close()
        out.close()
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

fun AssetManager.copyAssetFolder(srcName: String, dstName: String): Boolean {
    return try {
        var result = true
        val fileList = this.list(srcName) ?: return false
        if (fileList.isEmpty()) {
            result = copyAssetFile(srcName, dstName)
        } else {
            val file = File(dstName)
            result = file.mkdirs()
            for (filename in fileList) {
                result = result and copyAssetFolder(
                    srcName + separator.toString() + filename,
                    dstName + separator.toString() + filename
                )
            }
        }
        result
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}
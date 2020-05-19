package cm.aptoide.pt.install

import android.os.Build
import android.os.Environment
import android.os.StatFs
import cm.aptoide.pt.database.realm.Download
import cm.aptoide.pt.utils.FileUtils

class InstallAppSizeValidator(val filePathProvider: FilePathProvider) {

  fun hasEnoughSpaceToInstallApp(download: Download): Boolean {
    if (isAppAlreadyDownloaded(download)) {
      return true
    } else {
      return download.size <= getAvailableSpace()
    }
  }

  private fun getAvailableSpace(): Long {
    val stat = StatFs(Environment.getDataDirectory().path)
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
      (stat.availableBlocks * stat.blockSize).toLong()
    } else {
      stat.availableBlocksLong * stat.blockSizeLong
    }
  }


  private fun isAppAlreadyDownloaded(download: Download): Boolean {
    if (download.filesToDownload.isEmpty()) {
      return false
    } else {
      for (fileToDownload in download.filesToDownload) {
        if (!FileUtils.fileExists(
                filePathProvider.getFilePathFromFileType(
                    fileToDownload) + fileToDownload.fileName)) {
          return false
        }
      }
      return true
    }

  }
}
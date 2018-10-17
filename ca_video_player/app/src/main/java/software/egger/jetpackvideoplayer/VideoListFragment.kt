package software.egger.jetpackvideoplayer

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import software.egger.jetpackvideoplayer.adapters.VideoAdapter
import software.egger.jetpackvideoplayer.viewmodels.VideoListViewModel
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import software.egger.jetpackvideoplayer.data.VideoRepository
import software.egger.jetpackvideoplayer.viewmodels.VideoListViewModelFactory


class VideoListFragment : Fragment() {

    private lateinit var viewModel: VideoListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        context ?: return view

        val factory = VideoListViewModelFactory(VideoRepository)
        viewModel = ViewModelProviders.of(this, factory).get(VideoListViewModel::class.java)

        requestUpdateVideoList()

        val adapter = VideoAdapter()
        view.findViewById<RecyclerView>(R.id.video_list).adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_video_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh_video_list -> {
                requestUpdateVideoList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: VideoAdapter) {
        viewModel.getAllVideos().observe(viewLifecycleOwner, Observer { videos ->
            if (videos != null) adapter.submitList(videos)
        })
    }

    private val permissionRequestExternalStorage = 1

    private fun requestUpdateVideoList() {
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    permissionRequestExternalStorage)
        } else {
            updateVideoList()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == permissionRequestExternalStorage) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateVideoList()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun updateVideoList() {
        VideoRepository.updateVideoFiles()
    }
}

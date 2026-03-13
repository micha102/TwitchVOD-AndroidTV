package meta11ica.tn.twitchvod

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.leanback.widget.VerticalGridPresenter
import org.json.JSONArray
import java.util.Timer


/**
 * Loads a grid of cards with movies to browse.
 */
class ChannelFragment : VerticalGridSupportFragment() {

    private val mHandler = Handler(Looper.myLooper()!!)
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private lateinit var mSelectedChannel: Channel
    private lateinit var allStreams: List<Movie>
    var screenWidth: Int = 0
    var screenHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenWidth = resources.displayMetrics.widthPixels
        screenHeight = resources.displayMetrics.heightPixels
        mSelectedChannel = requireActivity().intent.getSerializableExtra(ChannelActivity.CHANNEL) as Channel
        prepareBackgroundManager()
        loadRows()
        setupEventListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        val backBtn = inflater.inflate(R.layout.button_back, view, false)
        backBtn.setOnClickListener(View.OnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        })
        view.addView(backBtn)
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        mBackgroundTimer?.cancel()
    }

    private fun prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(requireActivity().window)
        mDefaultBackground = ContextCompat.getDrawable(requireActivity(), R.drawable.default_background)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun loadRows() {


        val items = ArrayObjectAdapter(StreamPresenter(screenWidth/(NUM_COLS +1),screenHeight*7/24))
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        val queryResult = Utils.makeTwitchPostRequest(listOf(mSelectedChannel.login!!))

        allStreams = Utils.getLiveAndVODStreams(queryResult,mSelectedChannel.login!!)
        items.addAll(0,allStreams)
        title = mSelectedChannel.displayName


        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = NUM_COLS // Set the number of columns for the grid
        setGridPresenter(gridPresenter)
        adapter = items

    }

    private fun setupEventListeners() {
        onItemViewClickedListener = ItemViewClickedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {

            if (item is Movie) {
                val intent = Intent(activity!!, PlaybackActivity::class.java)
                intent.putExtra(PlaybackActivity.MOVIE, item)
                intent.putExtra(PlaybackActivity.CONFIRMATION_PROMPT, false)
                startActivity(intent)

            }

        }
    }



    fun resetBackground(activity: FragmentActivity?)
    {

        BackgroundManager.getInstance(activity)
        val backgroundDrawable: Drawable? = requireContext().getDrawable(R.drawable.default_background)
        val myView: View? = view?.rootView
        if (myView != null) {
            myView.background = backgroundDrawable
        }
    }

    companion object {
        private val TAG = "ChannelFragment"

        private val BACKGROUND_UPDATE_DELAY = 300
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        private val NUM_COLS = 4
    }
}
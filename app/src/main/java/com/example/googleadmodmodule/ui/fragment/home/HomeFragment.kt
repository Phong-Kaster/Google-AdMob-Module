package com.example.googleadmodmodule.ui.fragment.home

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.googleadmodmodule.MyApplication
import com.example.googleadmodmodule.R
import com.example.googleadmodmodule.admob.AdManagerAppOpen
import com.example.googleadmodmodule.core.CoreFragment
import com.example.googleadmodmodule.databinding.FragmentHomeBinding
import com.example.googleadmodmodule.notification.lockscreen.LockscreenManager
import com.example.googleadmodmodule.notification.normal.NotificationManger
import com.example.googleadmodmodule.notification.normal.NotificationReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : CoreFragment<FragmentHomeBinding>(
    layoutRes = R.layout.fragment_home
), HomeNavigator {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onResume() {
        super.onResume()
        AdManagerAppOpen.getInstance().enableAppOpenAd()
        //preload native ads for AdNativeFragment
        MyApplication.adManager.adNativeStandard.loadAd(activity = requireActivity())
        MyApplication.adManager.adNativeFullSize.loadAd(activity = requireActivity())
        MyApplication.adManager.adNativeMediumSize.loadAd(activity = requireActivity())
    }

    override fun onPause() {
        super.onPause()
        AdManagerAppOpen.getInstance().disableAppOpenAd()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun setupData() {
        super.setupData()
        makeSomeFakeActionToDatabase()
        setupNotification()
    }


    override fun setupView() {
        super.setupView()
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun navigateFromHomeToDestination(destination: NavDirections) {
        findNavController().navigate(destination)
    }

    private fun makeSomeFakeActionToDatabase() {
        //1. Insert some data into Room database
        viewModel.getUserInfo(1)
        viewModel.insertNationality()

        //2. observe what happens
        viewModel.userRelation.observe(viewLifecycleOwner) { userInfo ->
            if (userInfo != null) {
                Log.d(TAG, "userInfo: ${userInfo.userEntity.firstName} ${userInfo.userEntity.lastName}")
                Log.d(TAG, "userInfo: ${userInfo.nationalityAtBirth.nation}")
                Log.d(TAG, "userInfo: ${userInfo.playlists.size}")
            }
        }

        viewModel.nationalityId.observe(viewLifecycleOwner) { nationalityId ->
            Log.d(TAG, "nationalityId: $nationalityId")
        }
    }


    /********************ALL FUNCTIONS BELOWS ARE USED FOR SETTING DAILY NOTIFICATIONS***********************/
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setupNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isPermissionAccessed = NotificationManger.isNotificationAccessed(requireContext())
            if (!isPermissionAccessed) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }


        LockscreenManager.createLockscreenChannel(context = requireContext())
        LockscreenManager.setupLockscreenNotification(context = requireContext())


        NotificationManger.createNotificationChannel(context = requireContext())
        NotificationManger.setupNotification(context = requireContext())
    }

    /**
     * Register the permissions callback, which handles the user's response to the
     * system permissions dialog. Save the return value, an instance of
     * ActivityResultLauncher. You can use either a val, as shown in this snippet,
     * or a  var in your onAttach() or onCreate() method.
     * */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                LockscreenManager.setupLockscreenNotification(context = requireContext())
                NotificationManger.setupNotification(context = requireContext())
                showToast(getString(R.string.thank_you_and_many_blessings_to_you))
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    openRationaleDialog()
                } else {
                    openSettingDialog()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openRationaleDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.permission_required))
        builder.setMessage(getString(R.string.permission_required_content_1))
        builder.setPositiveButton(R.string.ok) { dialog, which ->
            dialog.cancel()
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        builder.show()
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openSettingDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.permission_required))
        builder.setMessage(getString(R.string.permission_required_content_2))
        builder.setPositiveButton(R.string.ok) { dialog, which ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${context?.packageName}")
            requestPermissionSettingLauncher.launch(intent)
        }
        builder.setNegativeButton(R.string.no_thanks, null)
        builder.show()
    }

    /*open app setting to grant this permission*/
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private var requestPermissionSettingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isPermissionAccessed = NotificationManger.isNotificationAccessed(requireContext())
            if (!isPermissionAccessed) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                /*set a recurring notification at 6 AM every day*/
                LockscreenManager.setupLockscreenNotification(context = requireContext())
                NotificationManger.setupNotification(context = requireContext())
            }
        }
}
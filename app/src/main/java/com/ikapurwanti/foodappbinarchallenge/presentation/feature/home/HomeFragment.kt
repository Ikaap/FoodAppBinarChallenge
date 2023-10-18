package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.dummy.DummyCategoriesDataSource
import com.ikapurwanti.foodappbinarchallenge.data.dummy.DummyCategoriesDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.MenuDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.appDataStore
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.MenuRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.FragmentHomeBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.detailmenu.DetailMenuActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter.AdapterLayoutMode
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter.CategoriesListAdapter
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter.MenuListAdapter
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelperImpl
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapterMenu: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR) {
            navigateToDetailMenu(it)
        }
    }
    private val adapterCategories = CategoriesListAdapter()
    private val datasourceCategories: DummyCategoriesDataSource by lazy {
        DummyCategoriesDataSourceImpl()
    }

    private fun navigateToDetailMenu(menu: Menu) {
        DetailMenuActivity.startActivity(requireContext(), menu)
    }

    private val viewModel: HomeViewModel by viewModels {
        val database = AppDatabase.getInstance(requireContext())
        val menuDao = database.menuDao()
        val menuDataSource = MenuDatabaseDataSource(menuDao)
        val repo: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val appPreferenceDataSource = AppPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(HomeViewModel(repo, appPreferenceDataSource))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvListCategories()
        rvListMenu()
        observeListMenu()
        observeLayout()
        setupSwitchLayout()
    }

    private fun rvListCategories() {
        binding.rvCategories.adapter = adapterCategories
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterCategories.setData(datasourceCategories.getCategories())
    }

    private fun rvListMenu() {
        val span = if (adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenuList.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = adapterMenu
            adapterMenu.refreshList()
        }
    }

    private fun observeListMenu() {
        viewModel.menuList.observe(viewLifecycleOwner) { result ->
            val span = if (adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvMenuList.isVisible = true
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvMenuList.apply {
                        layoutManager = GridLayoutManager(requireContext(), span)
                        adapter = this@HomeFragment.adapterMenu
                    }
                    result.payload?.let { item ->
                        adapterMenu.setData(item)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvMenuList.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvMenuList.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_menu_list_empty)
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvMenuList.isVisible = false
                }
            )
        }
    }

    private fun observeLayout() {
        viewModel.appLayoutGridLiveData.observe(viewLifecycleOwner) { isGridLayout ->
            binding.ivSwitchGrid.isGone = isGridLayout
            binding.ivSwitchList.isGone = !isGridLayout
            (binding.rvMenuList.layoutManager as GridLayoutManager).spanCount =
                if (isGridLayout) 2 else 1
            adapterMenu.adapterLayoutMode =
                if (isGridLayout) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapterMenu.refreshList()

        }
    }

    private fun setupSwitchLayout() {
        binding.ivSwitchGrid.setOnClickListener {
            viewModel.setAppLayoutPref(isGridLayout = true)
        }
        binding.ivSwitchList.setOnClickListener {
            viewModel.setAppLayoutPref(isGridLayout = false)
        }
    }

}
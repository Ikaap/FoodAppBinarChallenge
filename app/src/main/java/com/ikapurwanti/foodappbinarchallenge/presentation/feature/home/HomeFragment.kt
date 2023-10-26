package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.FragmentHomeBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.detailmenu.DetailMenuActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter.AdapterLayoutMode
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter.CategoriesListAdapter
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter.MenuListAdapter
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel : HomeViewModel by viewModel()

    private val adapterMenu: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR) {
            navigateToDetailMenu(it)
        }
    }

    private fun navigateToDetailMenu(menu: Menu) {
        DetailMenuActivity.startActivity(requireContext(), menu)
    }

    private val adapterCategories: CategoriesListAdapter by lazy {
        CategoriesListAdapter{
            viewModel.getMenu(it.name)
        }
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

        rvListMenu()
        getData()
        observeData()
        observeLayout()
        setupSwitchLayout()
    }

    private fun rvListMenu() {
        val span = if (adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenuList.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = adapterMenu
            adapterMenu.refreshList()
        }
    }
    private fun getData() {
        viewModel.getCategories()
        viewModel.getMenu()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategories.isVisible = true
                    binding.layoutStateCategory.root.isVisible = false
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategories.apply {
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        adapter = adapterCategories
                    }
                    it.payload?.let { data ->
                        adapterCategories.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvCategories.isVisible = false
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                },
                doOnError = {
                    binding.rvCategories.isVisible = false
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = it.exception?.message
                },
                doOnEmpty = {
                    binding.rvCategories.isVisible = false
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = getString(R.string.text_category_not_found)
                }
            )
        }

        viewModel.menu.observe(viewLifecycleOwner){
            val span = if (adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvMenuList.isVisible = true
                    binding.layoutStateMenu.root.isVisible = false
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = false
                    binding.rvMenuList.apply {
                        layoutManager = GridLayoutManager(requireContext(),span)
                        adapter = adapterMenu
                    }
                    it.payload?.let { data ->
                        adapterMenu.setData(data)
                        binding.rvMenuList.smoothScrollToPosition(0)
                    }
                },
                doOnLoading = {
                    binding.rvMenuList.isVisible = false
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = true
                    binding.layoutStateMenu.tvError.isVisible = false
                },
                doOnError = {
                    binding.rvMenuList.isVisible = false
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = true
                    binding.layoutStateMenu.tvError.text = it.exception?.message
                },
                doOnEmpty = {
                    binding.rvMenuList.isVisible = false
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = true
                    binding.layoutStateMenu.tvError.text = getString(R.string.text_menu_not_found)
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
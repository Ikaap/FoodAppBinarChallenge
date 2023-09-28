package com.ikapurwanti.foodappbinarchallenge.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.CategoriesDataSource
import com.ikapurwanti.foodappbinarchallenge.data.CategoriesDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.MenuDataSource
import com.ikapurwanti.foodappbinarchallenge.data.MenuDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.FragmentHomeBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.home.adapter.AdapterLayoutMode
import com.ikapurwanti.foodappbinarchallenge.presentation.home.adapter.CategoriesListAdapter
import com.ikapurwanti.foodappbinarchallenge.presentation.home.adapter.MenuListAdapter

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private val datasourceMenu: MenuDataSource by lazy {
        MenuDataSourceImpl()
    }

    private val adapterMenu: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
        }
    }

    private val datasourceCategories: CategoriesDataSource by lazy {
        CategoriesDataSourceImpl()
    }

    private val adapterCategories = CategoriesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerCategories()
        showRecyclerMenu()
        setupSwitch()
    }

    private fun showRecyclerCategories() {
        binding.rvCategories.adapter = adapterCategories
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterCategories.setData(datasourceCategories.getCategories())
    }

    private fun showRecyclerMenu() {
        val span = if(adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenuList.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this.adapter
        }
        adapterMenu.setData(datasourceMenu.getMenu())
    }

    private fun setupSwitch() {
        binding.ivSwitchGrid.setOnClickListener {
            if (true) {
                (binding.rvMenuList.layoutManager as GridLayoutManager).spanCount = 2
                adapterMenu.adapterLayoutMode = AdapterLayoutMode.GRID
                adapterMenu.refreshList()
            } else {
                binding.ivSwitchGrid.setImageResource(R.drawable.ic_list)
                (binding.rvMenuList.layoutManager as GridLayoutManager).spanCount = 1
                adapterMenu.adapterLayoutMode = AdapterLayoutMode.LINEAR
                adapterMenu.refreshList()
            }
        }
    }
}


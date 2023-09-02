package com.aurora.store.view.ui.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aurora.Constants
import com.aurora.gplayapi.data.models.editor.EditorChoiceBundle
import com.aurora.gplayapi.data.models.editor.EditorChoiceCluster
import com.aurora.store.R
import com.aurora.store.databinding.FragmentForYouBinding
import com.aurora.store.view.epoxy.controller.EditorChoiceController
import com.aurora.store.viewmodel.editorschoice.AppEditorChoiceViewModel
import com.aurora.store.viewmodel.editorschoice.BaseEditorChoiceViewModel
import com.aurora.store.viewmodel.editorschoice.GameEditorChoiceViewModel

class EditorChoiceFragment : BaseFragment(), EditorChoiceController.Callbacks {

    private lateinit var B: FragmentForYouBinding
    private lateinit var C: EditorChoiceController
    private lateinit var VM: BaseEditorChoiceViewModel

    private var pageType = 0

    companion object {
        @JvmStatic
        fun newInstance(pageType: Int): EditorChoiceFragment {
            return EditorChoiceFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.PAGE_TYPE, pageType)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        B = FragmentForYouBinding.bind(
            inflater.inflate(
                R.layout.fragment_for_you,
                container,
                false
            )
        )

        C = EditorChoiceController(this)

        val bundle = arguments
        if (bundle != null) {
            pageType = bundle.getInt(Constants.PAGE_TYPE, 0)
        }

        when (pageType) {
            0 -> VM = ViewModelProvider(this)[AppEditorChoiceViewModel::class.java]
            1 -> VM = ViewModelProvider(this)[GameEditorChoiceViewModel::class.java]
        }

        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        B.recycler.setController(C)

        VM.liveData.observe(viewLifecycleOwner) {
            updateController(it)
        }
    }

    private fun updateController(editorChoiceBundles: List<EditorChoiceBundle>) {
        C.setData(editorChoiceBundles)
    }

    override fun onClick(editorChoiceCluster: EditorChoiceCluster) {
        openEditorStreamBrowseFragment(
            editorChoiceCluster.clusterBrowseUrl,
            editorChoiceCluster.clusterTitle
        )
    }
}

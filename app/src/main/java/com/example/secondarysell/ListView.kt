package com.example.secondarysell

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list_view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListView.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListView : Fragment(),GoodsListAdapater.MyItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
     var myAdapter:GoodsListAdapater?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myAdapter= GoodsListAdapater(context!!)
        rcview1.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)

        myAdapter!!.readallgoods()
        myAdapter!!.setMyItemClickListener(this)

        rcview1.addItemDecoration(
            ListPaddingDecoration(context!!,0,0)
        )
        rcview1.adapter = myAdapter

        rcview1.itemAnimator?.addDuration = 1000L
        rcview1.itemAnimator?.removeDuration = 1000L
        rcview1.itemAnimator?.moveDuration = 1000L
        rcview1.itemAnimator?.changeDuration = 1000L

        btnsortbyprice.setOnClickListener {
            myAdapter!!.sortbyprice()
        }

        btnsortbytitle.setOnClickListener {
            myAdapter!!.sortbytitle()
        }
    }

    override fun onItemClickedFromAdapter(good:GoodsData) {
        onItemClicked(good)
    }


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onItemClicked(good: GoodsData) {
        listener?.onGoodClicked(good)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onGoodClicked(good: GoodsData)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

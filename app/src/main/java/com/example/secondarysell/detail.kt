package com.example.secondarysell

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.*
import java.io.Serializable
import java.util.zip.GZIPInputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [detail.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [detail.newInstance] factory method to
 * create an instance of this fragment.
 */
class detail : Fragment() {
    // TODO: Rename and change types of parameters
    private var good: GoodsData? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            good = it.getSerializable(ARG_PARAM1) as GoodsData
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(good!!.imageid!=-1) {
            detailimage.setImageResource(good!!.imageid!!)
        }
        detailname.text="Name: "+good!!.title
        detailprice.text="Price: "+good!!.price.toString()+"$"
        detaildes.text="Description: "+good!!.overview

        btnaddtocart.setOnClickListener {
            onADDCARTPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onADDCARTPressed() {
        listener?.onADDCARTFragmentInteraction()
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
        fun onADDCARTFragmentInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(good: GoodsData) =
            detail().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, good as Serializable)
                }
            }
    }
}

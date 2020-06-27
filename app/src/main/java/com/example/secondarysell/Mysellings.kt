package com.example.secondarysell

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_mysellings.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Mysellings.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Mysellings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Mysellings : Fragment(),GoodsListAdapater.MyItemClickListener {
    // TODO: Rename and change types of parameters
    private var user: UserData? = null
    var myAdapter:GoodsListAdapater?=null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable(ARG_PARAM1) as UserData

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter= GoodsListAdapater(context!!,user,1)
        rcview2.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 1)

        myAdapter!!.getmysellinggoods()
        myAdapter!!.setMyItemClickListener(this)

        rcview2.addItemDecoration(
            ListPaddingDecoration(context!!,0,0)
        )
        rcview2.adapter = myAdapter

        rcview2.itemAnimator?.addDuration = 1000L
        rcview2.itemAnimator?.removeDuration = 1000L
        rcview2.itemAnimator?.moveDuration = 1000L
        rcview2.itemAnimator?.changeDuration = 1000L

        btnaddsellgood.setOnClickListener {
            onADDPressed()
        }

        btndeletegood.setOnClickListener {
            onDELETEPressed(myAdapter!!.selectedgoods())
        }
    }
    override fun onItemClickedFromAdapter(good:GoodsData) {
            Toast.makeText(context,"clicked from my selling",Toast.LENGTH_LONG).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mysellings, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onADDPressed() {
        listener?.onADDFragmentInteraction()
    }

    fun onDELETEPressed(goods:ArrayList<GoodsData>) {
        listener?.onDELETEFragmentInteraction(goods)
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
        fun onADDFragmentInteraction()
        fun onDELETEFragmentInteraction(goods:ArrayList<GoodsData>)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Mysellings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user:UserData) =
            Mysellings().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, user as Serializable)
                }
            }
    }
}

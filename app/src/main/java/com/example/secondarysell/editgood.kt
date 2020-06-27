package com.example.secondarysell

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_editgood.*
import java.io.Serializable
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [editgood.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [editgood.newInstance] factory method to
 * create an instance of this fragment.
 */
class editgood : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: UserData? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        btneditgoodsubmit.setOnClickListener {
            if(editgooddescription.text!=null && editgoodprice.text!=null && editgoodtitle!=null) {
                var strprice= editgoodprice.text.toString()
                var doubleprice = strprice.toDouble()
                var newgood = GoodsData(
                    editgoodtitle.text.toString(),
                    -1,
                    editgooddescription.text.toString(),
                    doubleprice,
                    -1,
                    user!!.username
                )

                onSubmitPressed(newgood)
            }
        }

        btneditgoodcancle.setOnClickListener {

            onCancelPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable(ARG_PARAM1) as UserData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editgood, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onSubmitPressed(good: GoodsData) {
        listener?.onSUBMITFragmentInteraction(good)
    }

    fun onCancelPressed() {
        listener?.onCANCELragmentInteraction()
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
        fun onSUBMITFragmentInteraction(good: GoodsData)
        fun onCANCELragmentInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment editgood.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user:UserData) =
            editgood().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, user as Serializable)
                }
            }
    }
}

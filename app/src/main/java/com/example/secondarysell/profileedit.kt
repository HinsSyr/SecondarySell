package com.example.secondarysell

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_profileedit.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [profileedit.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [profileedit.newInstance] factory method to
 * create an instance of this fragment.
 */
class profileedit : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: UserData? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable(ARG_PARAM1) as UserData

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editfirstname.setText(user!!.firstname)
        editlastname.setText(user!!.lastname)
        editemail.setText(user!!.email)
        editusername.text=user!!.username
        editpassword.setText(user!!.password.toString())


        btnprofilesave.setOnClickListener {
            if(editrepassword.text.toString()== editpassword.text.toString()) {
                val strpassword = editpassword.text.toString()
                val intpassword = Integer.parseInt(strpassword)

                var newuser = UserData(
                    editusername.text.toString(), intpassword, editfirstname.text.toString(),
                    editlastname.text.toString(), editemail.text.toString()
                )
                onSavePressed(newuser)
            }
            else
            {
                Toast.makeText(context,"The password isn't match",Toast.LENGTH_LONG).show()
            }
        }

        btnprofilecancle.setOnClickListener {
            onCancelPressed()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profileedit, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onSavePressed(newuser: UserData) {
        listener?.onSAVEFragmentInteraction(newuser)

    }
    fun onCancelPressed()
    {

        listener?.onCANCELFragmentInteraction()
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
        fun onSAVEFragmentInteraction(newuser:UserData)
        fun onCANCELFragmentInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment profileedit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user:UserData) =
            profileedit().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, user as Serializable)
                }
            }
    }
}

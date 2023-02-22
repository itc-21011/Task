package jp.ac.it_college.std.s21011.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import jp.ac.it_college.std.s21011.task.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    internal val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val helper = DBHelper(requireContext())
        val sql = "SELECT Item_id as _id, Item_name FROM items"
        val cursor = helper.readableDatabase.rawQuery(sql, null)

        binding.spinner.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            cursor,
            arrayOf("Item_name"),
            intArrayOf(android.R.id.text1),
            FLAG_REGISTER_CONTENT_OBSERVER
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
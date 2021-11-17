package com.example.frontend;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.frontend.entity.Card;
import com.example.frontend.retrofit.IRetrofit;
import com.example.frontend.retrofit.RetrofitClient;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class BlankFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment2() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private IRetrofit iRetrofit;
    private Context context;

    private List<Card> list;          // 데이터를 넣은 리스트변수
    private ArrayList<Card> items = new ArrayList<>();
    private ListView theListView;
    private EditText editSearch;
    private FoldingCellListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_blank2, container, false);

        editSearch = rootView.findViewById(R.id.editSearch);
        theListView = rootView.findViewById(R.id.mainListView);

        list = new ArrayList<Card>();

        if (getArguments() != null) {
            String userNo = getArguments().getString("userNo"); // 프래그먼트1에서 받아온 값 넣기

            //retrofit 생성
            iRetrofit = RetrofitClient.getClient().create(IRetrofit.class);
            Call<Card> call = iRetrofit.selectFriendsInfoList(userNo);
            call.enqueue(new Callback<Card>() {
                @Override
                public void onResponse(Call<Card> call, Response<Card> response) {
                    Log.d("retrofit", "Data fetch success");

                    if (response.isSuccessful() && response.body() != null) {
                        ArrayList<Card> friendsInfoList = response.body().getFriendsInfoList();

                        int a = 0;
                        if (friendsInfoList != null) {
                            for (Card friendsInfo : friendsInfoList) {
                                a++;

                                list.add(friendsInfo);
                            }
                            items.addAll(list);

                            context = container.getContext();

                            // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                            adapter = new FoldingCellListAdapter(getContext(), items, (ShareActivity) getActivity());

                            // set elements to adapter
                            theListView.setAdapter(adapter);

                            // set on click event listener to list view
                            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                    // toggle clicked cell state
                                    ((FoldingCell) view).toggle(false);
                                    // register in adapter that state for selected cell is toggled
                                    adapter.registerToggle(pos);
                                }
                            });

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("알림")
                                    .setMessage("목록 정보가 없습니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                        }


                    }
                }

                @Override
                public void onFailure(Call<Card> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("BlankFragment2 Retrofit 알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();

                }
            });
        }

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                Log.d("textChanged Find!!!!!!!!: ", text);
                search(text);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        list.clear();

        if (charText.length() == 0) {
            list.addAll(items);
        }else
        {
            for(int i = 0;i < items.size(); i++)
            {
                if (items.get(i).getUserName().contains(charText) || items.get(i).getUserName().toLowerCase().contains(charText))
                {
                    list.add(items.get(i));
                }
            }
        }
        //adapter.notifyDataSetChanged();
        adapter = new FoldingCellListAdapter(getContext(), list, (ShareActivity) getActivity());

        // set elements to adapter
        theListView.setAdapter(adapter);
    }


}
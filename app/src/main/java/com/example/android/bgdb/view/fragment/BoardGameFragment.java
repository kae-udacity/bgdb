package com.example.android.bgdb.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.android.bgdb.model.BoardGame;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoardGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardGameFragment extends Fragment implements BaseView {

    private OnFragmentInteractionListener listener;
    private List<BoardGame> boardGames;

    public BoardGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment BoardGameFragment.
     */
    public static BoardGameFragment newInstance() {
        return new BoardGameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    public void clearBoardGames() {
        this.boardGames = null;
    }

    @Override
    public void onPostLoad(List<BoardGame> boardGames) {
        this.boardGames = boardGames;
        BaseListViewImpl listFragment = (BaseListViewImpl) getTargetFragment();
        if (listFragment != null) {
            listFragment.onPostLoad(boardGames);
        }
    }

    @Override
    public void onPreLoad() {

    }
}

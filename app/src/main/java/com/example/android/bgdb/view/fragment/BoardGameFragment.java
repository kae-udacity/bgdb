package com.example.android.bgdb.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.android.bgdb.model.BoardGame;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentListener} interface
 * to handle interaction events.
 * Use the {@link BoardGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardGameFragment extends Fragment implements BoardGameView {

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
    public void setBoardGames(List<BoardGame> boardGames) {
        this.boardGames = boardGames;
    }

    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    public void clearBoardGames() {
        this.boardGames = null;
    }
}

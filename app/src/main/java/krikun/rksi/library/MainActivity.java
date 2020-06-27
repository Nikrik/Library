/*
Для книг, хранящихся в библиотеке, задаются регистрационный
номер книги, автор, название, год издания, издательство, количество страниц.
Вывести список книг с фамилиями авторов, изданных после заданного года.
*/

package krikun.rksi.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    int mPosition;//переменная позиции при редактировании книги
    MenuItem addmenu;//переменная меню добавления
    MenuItem searchmenu;//переменная меню поиска
    MenuItem exitmenu;//переменная меню выхода из поиска
    AlertDialog alertDialog;// переменная диалогового окна с выборам года
    private boolean instans;//переменная, определяющая нужно ли обновлять весь список книг
    ArrayList<Book> search=new ArrayList<>();//список книг при поиске
    ArrayList<Book> books = new ArrayList<>();//список книг без поиска
    private RecyclerView mBookRecyclerView;//RecyclerView для вывода на экран
    private BookAdapter mAdapter;//переменная- буфер
    int year;//год поиска

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mAutorTextView;
        private Book mBook;


        @Override
        public void onClick(View v)
        {
            if (false)
            {

            } else
                {
                mPosition = getAdapterPosition();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("number", mBook.number);
                intent.putExtra("autor", mBook.autor);
                intent.putExtra("name", mBook.name);
                intent.putExtra("year", mBook.year);
                intent.putExtra("publisher", mBook.publisher);
                intent.putExtra("pages", mBook.pages);
                startActivityForResult(intent, 2);
            }
        }

        BookHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_book_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_book_date_text_view);
            mAutorTextView = (TextView) itemView.findViewById(R.id.list_item_book_autor_text_view);
        }

        @SuppressLint("SetTextI18n")
        void bindBook(Book book)
        {
            mBook = book;
            mTitleTextView.setText(mBook.name);
            mDateTextView.setText(Integer.toString(mBook.year));
            mAutorTextView.setText((mBook.autor));
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder>
    {
        private List<Book> mBooks;
        BookAdapter(List<Book> books)
        {
            mBooks = books;
        }

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_book, parent, false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position)
        {
            Book book = mBooks.get(position);
            holder.bindBook(book);
        }

        @Override
        public int getItemCount()
        {
            return mBooks.size();
        }

        void setBooks(List<Book> books)
        {
            mBooks = books;
        }
    }




    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        mBookRecyclerView = (RecyclerView) findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.activity_search, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                if (userInput.getText().toString().isEmpty())
                                {
                                    Toast.makeText(MainActivity.this,getString(R.string.enter)+" "+userInput.getHint(),Toast.LENGTH_LONG).show();
                                    alertDialog.show();
                                    return;
                                }
                                year=Integer.parseInt(userInput.getText().toString());
                                for (int i = 0;i < search.size(); i++)
                                {
                                    if (search.get(i).year>year)
                                    {
                                        books.add(search.get(i));
                                    }
                                }
                                instans=true;
                                updateUI();
                                addmenu.setVisible(false);
                                searchmenu.setVisible(false);
                                exitmenu.setVisible(true);
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        alertDialog=mDialogBuilder.create();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                startActivityForResult(new Intent(this, AddActivity.class),1);
                break;
            case R.id.item2:

                search.addAll(books);
                books.clear();
                alertDialog.show();
                break;
            case R.id.item3:
                books.clear();
                addmenu.setVisible(true);
                searchmenu.setVisible(true);
                exitmenu.setVisible(false);
                books.addAll(search);
                search.clear();
                instans=true;
                updateUI();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI()
    {
        if(books.size() == 0)
            Toast.makeText(this, R.string.list_empty, Toast.LENGTH_SHORT).show();
        if(mAdapter == null)
        {
            mAdapter = new BookAdapter(books);
            mBookRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setBooks(books);
            if (!instans)
            {
                mAdapter.notifyItemChanged(mPosition);
                mAdapter.notifyItemRemoved(books.size());
            }
            else
            {
                mAdapter.notifyDataSetChanged();
                instans=false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            if (data != null)
            {
                if (requestCode == 1)
                {
                    books.add(new Book(
                            data.getIntExtra("number", 0),
                            data.getStringExtra("autor"),
                            data.getStringExtra("name"),
                            data.getIntExtra("year", 0),
                            data.getStringExtra("publisher"),
                            data.getIntExtra("pages", 0)
                    ));
                    updateUI();
                }
                else if (requestCode==2)
                {
                    books.set(mPosition,
                            new Book(
                            data.getIntExtra("number", 0),
                            data.getStringExtra("autor"),
                            data.getStringExtra("name"),
                            data.getIntExtra("year", 0),
                            data.getStringExtra("publisher"),
                            data.getIntExtra("pages", 0)
                            ));
                    updateUI();
                }
            }
        }
        else
        {
            Toast.makeText(this, R.string.canceled, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        addmenu=menu.findItem(R.id.item1);
        searchmenu=menu.findItem(R.id.item2);
        exitmenu=menu.findItem(R.id.item3);
        return super.onCreateOptionsMenu(menu);
    }
}

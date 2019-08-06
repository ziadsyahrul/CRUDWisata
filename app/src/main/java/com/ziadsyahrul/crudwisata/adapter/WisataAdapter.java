package com.ziadsyahrul.crudwisata.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.UI.detailwisata.DetailWisataActivity;
import com.ziadsyahrul.crudwisata.UI.detailwisatabyuser.DetailWisataByUserActivity;
import com.ziadsyahrul.crudwisata.UI.wisatabycategory.WisataByCategoryActivity;
import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;
    public static final int TYPE_5 = 5;
    Integer viewType;
    private final Context context;
    private final List<WisataData> wisataDataList;

    public WisataAdapter(Integer viewType, Context context, List<WisataData> wisataDataList) {
        this.context = context;
        this.wisataDataList = wisataDataList;
        this.viewType = viewType;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wisata_news, null);
                return new WisataNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wisata_populer, null);
                return new WisataPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wisata_kategori, null);
                return new WisataKategoryViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wisata_by_category, null);
                return new WisataNewsViewHolder(view);
            case TYPE_5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wisata_by_category, null);
                return new WisataByUserViewHolder(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final WisataData wisataData = wisataDataList.get(i);

        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                WisataNewsViewHolder wisataNewsViewHolder = (WisataNewsViewHolder) viewHolder;
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(wisataData.getUrl_wisata()).apply(options).into(wisataNewsViewHolder.imgWisata);
                wisataNewsViewHolder.txtTitle.setText(wisataData.getNama_wisata());
                wisataNewsViewHolder.txtView.setText(wisataData.getView());
                wisataNewsViewHolder.txtTime.setText(newDate(wisataData.getInsert_time()));

                wisataNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailWisataActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, wisataData.getId_wisata()));
                    }
                });
                break;
            case TYPE_2:
                WisataPopulerViewHolder wisataPopulerViewHolder = (WisataPopulerViewHolder) viewHolder;
                RequestOptions options1 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(wisataData.getUrl_wisata()).apply(options1).into(wisataPopulerViewHolder.imgMakanan);
                wisataPopulerViewHolder.txtTitle.setText(wisataData.getNama_wisata());
                wisataPopulerViewHolder.txtView.setText(wisataData.getView());
                wisataPopulerViewHolder.txtTime.setText(wisataData.getInsert_time());

                wisataPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailWisataActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, wisataData.getId_wisata()));
                    }
                });
                break;
            case TYPE_3:
                WisataKategoryViewHolder wisataKategoryViewHolder = (WisataKategoryViewHolder) viewHolder;
                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(wisataData.getUrl_wisata()).apply(options2).into(wisataKategoryViewHolder.image);
                wisataKategoryViewHolder.txtNamaKategory.setText(wisataData.getNama_kategori());
                wisataKategoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, WisataByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_CATEGORY, wisataData.getId_kategori()));
                    }
                });
                break;
            case TYPE_4:
                WisataNewsViewHolder wisataNewsViewHolder1 = (WisataNewsViewHolder) viewHolder;
                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(wisataData.getUrl_wisata()).apply(options3).into(wisataNewsViewHolder1.imgWisata);
                wisataNewsViewHolder1.txtTitle.setText(wisataData.getNama_wisata());
                wisataNewsViewHolder1.txtView.setText(wisataData.getView());
                wisataNewsViewHolder1.txtTime.setText(newDate(wisataData.getInsert_time()));
                wisataNewsViewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, WisataByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, wisataData.getId_wisata()));
                    }
                });
                break;
            case TYPE_5:
                WisataByUserViewHolder wisataByUserViewHolder = (WisataByUserViewHolder) viewHolder;
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(wisataData.getUrl_wisata()).apply(options4).into(wisataByUserViewHolder.imgWisata);
                wisataByUserViewHolder.txtTitle.setText(wisataData.getNama_wisata());
                wisataByUserViewHolder.txtView.setText(wisataData.getView());
                wisataByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailWisataByUserActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, wisataData.getId_wisata()));
                    }
                });
        }
    }

    public String newDate(String insert_time) {
        Date date = null;
        String newDate = insert_time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(insert_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }

        return newDate;
    }

    @Override
    public int getItemCount() {
        return wisataDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class WisataNewsViewHolder extends ViewHolder {

        @BindView(R.id.img_wisata)
        ImageView imgWisata;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;

        public WisataNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    class WisataPopulerViewHolder extends ViewHolder {

        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;


        public WisataPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    class WisataKategoryViewHolder extends ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public WisataKategoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

     class WisataByUserViewHolder extends ViewHolder {

        @BindView(R.id.img_wisata)
        ImageView imgWisata;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;

        public WisataByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}

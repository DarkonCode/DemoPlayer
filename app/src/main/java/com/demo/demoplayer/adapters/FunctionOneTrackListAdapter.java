package com.demo.demoplayer.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demoplayer.R;
import com.demo.demoplayer.interfaces.OnImageClickListener;
import com.demo.demoplayer.utils.DemoConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Brandon Lee Roets on 2016/06/21.
 */
public class FunctionOneTrackListAdapter extends RecyclerView.Adapter<FunctionOneTrackListAdapter.ViewHolder> {

    final private JsonArray trackList;
    final private OnImageClickListener onImageClickListener;
    final LayoutInflater inflater;

    public FunctionOneTrackListAdapter(OnImageClickListener onImageClickListener, final Context context) {

        trackList = new JsonParser().parse(DemoConstants.FUNCTION_ONE_TRACK_JSON).getAsJsonArray();
        this.onImageClickListener = onImageClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item_function_one_tracks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.bindTrack(trackList.get(position).getAsJsonObject(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onImageClickListener.imageClick(holder.getAdapterPosition());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public JsonObject getItemAtPosition(int position) {
        return trackList.get(position).getAsJsonObject();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.activity_functionOne_mainActivity_trackList_desc)
        TextView trackDesc;

        @BindView(R.id.activity_functionOne_mainActivity_trackList_image)
        SimpleDraweeView trackImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTrack(final JsonObject trackItem, final View.OnClickListener viewHolderImageClick) {

            int width = 100;
            int height = 100;

            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(trackItem.get(DemoConstants.TRACK_IMAGE_URL).getAsString()))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();

            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(trackImage.getController())
                    .setImageRequest(request)
                    .build();

            trackImage.setController(controller);
            trackDesc.setText(trackItem.get(DemoConstants.TRACK_DESCRIPTION).getAsString());

            trackImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolderImageClick.onClick(v);
                }
            });

            //TODO fix issue with clicking out side of fragment so that it stops playing the sound
        }
    }
}

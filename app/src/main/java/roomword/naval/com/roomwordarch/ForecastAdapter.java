package roomword.naval.com.roomwordarch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;


import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import java.util.Date;

import roomword.naval.com.roomwordarch.utils.SunshineDateUtils;
import roomword.naval.com.roomwordarch.utils.SunshineWeatherUtils;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>
{

    private static final String TAG = ForecastAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;
    private Context context;
    private List<ListWeatherEntry> list;//mForecast
    private OnItemClickListener onItemClickListener;
    private final boolean mUseTodayLayout;
    private final ForecastAdapterOnItemClickHandler mClickHandler;

    public ForecastAdapter(Context context,  ForecastAdapterOnItemClickHandler clickHandler)
    {
        this.context = context;
       // this.list = list;
        mClickHandler = clickHandler;
       // this.onItemClickListener = onItemClickListener;
        mUseTodayLayout = context.getResources().getBoolean(R.bool.use_today_layout);
    }

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        int layoutId = getLayoutIdByType(viewType);
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        view.setFocusable(true);
        return new ForecastAdapterViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position)
    {
        ListWeatherEntry item = list.get(position);
//Weather Icon
        int weatherIconId = item.getWeatherIconId();
        int weatherImageResourceId = getImageResourceId(weatherIconId, position);
        forecastAdapterViewHolder.iconView.setImageResource(weatherImageResourceId);
//Weather Date
        long dateInMillis = item.getDate().getTime();
        /* Get human readable string using our utility method */
        String dateString = SunshineDateUtils.getFriendlyDateString(context, dateInMillis, false);

        /* Display friendly date string */
        forecastAdapterViewHolder.dateView.setText(dateString);
 //Weather Description
        String description = SunshineWeatherUtils.getStringForWeatherCondition(context, weatherIconId);
        /* Create the accessibility (a11y) String from the weather description */
        String descriptionA11y = context.getString(R.string.a11y_forecast, description);

        /* Set the text and content description (for accessibility purposes) */
        forecastAdapterViewHolder.descriptionView.setText(description);
        forecastAdapterViewHolder.descriptionView.setContentDescription(descriptionA11y);
//High (max) temperature
        double highInCelsius = item.getMax();
        String highString = SunshineWeatherUtils.formatTemperature(context, highInCelsius);
        /* Create the accessibility (a11y) String from the weather description */
        String highA11y = context.getString(R.string.a11y_high_temp, highString);

        /* Set the text and content description (for accessibility purposes) */
        forecastAdapterViewHolder.highTempView.setText(highString);
        forecastAdapterViewHolder.highTempView.setContentDescription(highA11y);
//Low (min) temperature
        double lowInCelsius = item.getMin();
        String lowString = SunshineWeatherUtils.formatTemperature(context, lowInCelsius);
        String lowA11y = context.getString(R.string.a11y_low_temp, lowString);

        /* Set the text and content description (for accessibility purposes) */
        forecastAdapterViewHolder.lowTempView.setText(lowString);
        forecastAdapterViewHolder.lowTempView.setContentDescription(lowA11y);
        //Todo: Setup viewholder for item 

    }

    @Override
    public int getItemCount()
    {
        if (null == list) return 0;
        return list.size();

    }
    /**
     * Returns an integer code related to the type of View we want the ViewHolder to be at a given
     * position. This method is useful when we want to use different layouts for different items
     * depending on their position. In Sunshine, we take advantage of this method to provide a
     * different layout for the "today" layout. The "today" layout is only shown in portrait mode
     * with the first item in the list.
     *
     * @param position index within our RecyclerView and list
     * @return the view type (today or future day)
     */
    @Override
    public int getItemViewType(int position) {
        if (mUseTodayLayout && position == 0) {
            return VIEW_TYPE_TODAY;
        } else {
            return VIEW_TYPE_FUTURE_DAY;
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    private int getImageResourceId(int weatherIconId, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {

            case VIEW_TYPE_TODAY:
                return SunshineWeatherUtils
                        .getLargeArtResourceIdForWeatherCondition(weatherIconId);

            case VIEW_TYPE_FUTURE_DAY:
                return SunshineWeatherUtils
                        .getSmallArtResourceIdForWeatherCondition(weatherIconId);

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    /**
     * Returns the the layout id depending on whether the list item is a normal item or the larger
     * "today" list item.
     *
     * @param viewType
     * @return
     */
    private int getLayoutIdByType(int viewType) {
        switch (viewType) {

            case VIEW_TYPE_TODAY: {
                return R.layout.list_item_forecast_today;
            }

            case VIEW_TYPE_FUTURE_DAY: {
                return R.layout.forecast_list_item;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    /**
     * The interface that receives onItemClick messages.
     */
    public interface ForecastAdapterOnItemClickHandler {
        void onItemClick(Date date);
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView iconView;

        final TextView dateView;
        final TextView descriptionView;
        final TextView highTempView;
        final TextView lowTempView;
        ForecastAdapterViewHolder(View view) {
            super(view);

            iconView = view.findViewById(R.id.weather_icon);
            dateView = view.findViewById(R.id.date);
            descriptionView = view.findViewById(R.id.weather_description);
            highTempView = view.findViewById(R.id.high_temperature);
            lowTempView = view.findViewById(R.id.low_temperature);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v )
        {
            int adapterPosition = getAdapterPosition();
            Date date = list.get(adapterPosition).getDate();
            mClickHandler.onItemClick(date);
        }
    }
    void swapForecast(final List<ListWeatherEntry> newForecast) {
        // If there was no forecast data, then recreate all of the list
        if (list == null) {
            list = newForecast;
            notifyDataSetChanged();
        } else {
            /*
             * Otherwise we use DiffUtil to calculate the changes and update accordingly. This
             * shows the four methods you need to override to return a DiffUtil callback. The
             * old list is the current list stored in mForecast, where the new list is the new
             * values passed in from the observing the database.
             */

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return list.size();
                }

                @Override
                public int getNewListSize() {
                    return newForecast.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return list.get(oldItemPosition).getId() ==
                            newForecast.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ListWeatherEntry newWeather = newForecast.get(newItemPosition);
                    ListWeatherEntry oldWeather = list.get(oldItemPosition);
                    return newWeather.getId() == oldWeather.getId()
                            && newWeather.getDate().equals(oldWeather.getDate());
                }
            });
            list = newForecast;
            result.dispatchUpdatesTo(this);
        }
    }

}


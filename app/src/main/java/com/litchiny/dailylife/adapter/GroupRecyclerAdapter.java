package com.litchiny.dailylife.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.litchiny.dailylife.R;
import com.litchiny.dailylife.holder.EmptyFooterViewHolder;
import com.litchiny.dailylife.holder.MemberViewHolder;
import com.litchiny.dailylife.holder.TeamViewHolder;
import com.litchiny.dailylife.listener.OnChildClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * author: LL .
 * date:   On 2018/4/16
 * caption:
 */


public abstract class GroupRecyclerAdapter<G, GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder, MVH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter {
    private static final String TAG = "GroupRecyclerAdapter";
    private static final int INVALID_POSITION = -1;
    private static final int TYPE_GROUP = 1;
    private static final int TYPE_CHILD = 2;
    private static final int TYPE_FOOTER = 3;
    private LayoutInflater layoutInflater = null;
    private boolean VIEW_FOOTER;
    private List<G> mGroups;
    private int mItemCount;

    private OnChildClickListener mOnChildClickListener;

    public GroupRecyclerAdapter(List<G> groups) {
        mGroups = groups == null ? new ArrayList<G>() : groups;
        updateItemCount();
    }

    private void updateItemCount() {
        int count = 0;
        for (G group : mGroups) {
            count += getChildCount(group) + 1;
        }
        mItemCount = count;
    }

    protected abstract int getChildCount(G group);


    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        mOnChildClickListener = onChildClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_GROUP) {
            return onCreateGroupViewHolder(parent);
        } else if (viewType == TYPE_FOOTER)
            return onCreateFooterViewHolder(parent);
        else {
            final MemberViewHolder viewHolder = onCreateChildViewHolder(parent);
            viewHolder.tb_item_switch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnChildClickListener != null) {
                        final Position position = getGroupChildPosition(viewHolder.getAdapterPosition());
                        mOnChildClickListener.onToggleClick(v, position.group, position.child);
                    }
                }
            });

            viewHolder.ll_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnChildClickListener != null) {
                        final Position position = getGroupChildPosition(viewHolder.getAdapterPosition());
                        mOnChildClickListener.onChildClick(v, position.group, position.child);
                    }
                }
            });

            return viewHolder;
        }
    }

    private TeamViewHolder onCreateGroupViewHolder(ViewGroup parent) {
        return new TeamViewHolder(layoutInflater.inflate(R.layout.item_team_title, parent, false));
    }

    private EmptyFooterViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        return new EmptyFooterViewHolder(layoutInflater.inflate(R.layout.item_team_member_footer, parent, false));
    }

    private MemberViewHolder onCreateChildViewHolder(ViewGroup parent) {
        return new MemberViewHolder(layoutInflater.inflate(R.layout.item_team_member, parent, false));
    }

    private Position getGroupChildPosition(int itemPosition) {
        int itemCount = 0;
        int childCount;
        final Position position = new Position();
        for (G g : mGroups) {
            if (itemPosition == itemCount) {
                position.child = INVALID_POSITION;
                return position;
            }
            itemCount++;
            position.child = itemPosition - itemCount;
            childCount = getChildCount(g);
            if (position.child < childCount) {
                return position;
            }
            itemCount += childCount;
            position.group++;
        }
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int itemPosition) {
        if (!isFooterView(itemPosition)) {
            Position position = getGroupChildPosition(itemPosition);
            if (position.child == -1) {
                onBindGroupViewHolder((GVH) holder, position.group);
            } else {
                onBindChildViewHolder((CVH) holder, position.group, position.child);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemType type = getItemType(position);
        return type == ItemType.GROUP_TITLE ? TYPE_GROUP : type == ItemType.FOOTER ? TYPE_FOOTER : TYPE_CHILD;
    }

    @Override
    public int getItemCount() {
        return mItemCount + (VIEW_FOOTER ? 1 : 0);
    }

    public ItemType getItemType(final int itemPosition) {
        int count = 0;
        if (isFooterView(itemPosition)) {
            return ItemType.FOOTER;
        } else {
            for (G g : mGroups) {
                if (itemPosition == count) {
                    return ItemType.GROUP_TITLE;
                }
                count += 1;
                if (itemPosition == count) {
                    return ItemType.FIRST_CHILD;
                }
                count += getChildCount(g);
                if (itemPosition < count) {
                    return ItemType.NOT_FIRST_CHILD;
                }
            }
        }
        return null;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    protected abstract void onBindGroupViewHolder(GVH holder, int groupPosition);

    protected abstract void onBindChildViewHolder(CVH holder, int groupPosition, int childPosition);

    public boolean haveFooterView() {
        return VIEW_FOOTER;
    }

    public void addFooterView(boolean footerView) {
        if (!haveFooterView()) {
            VIEW_FOOTER = footerView;
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public int getGroupCount() {
        return mGroups.size();
    }

    public void add(List<G> groups) {
        int lastCount = getItemCount();
        addGroups(groups);
        updateItemCount();
        notifyItemRangeInserted(lastCount, mItemCount - lastCount);
    }

    private void addGroups(List<G> groups) {
        if (groups != null) {
            mGroups.addAll(groups);
        }
    }

    public void update(List<G> groups) {
        mGroups.clear();
        addGroups(groups);
        updateItemCount();
        notifyDataSetChanged();
    }

    protected G getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    public enum ItemType {
        GROUP_TITLE,
        FIRST_CHILD,
        NOT_FIRST_CHILD,
        FOOTER
    }

    class Position {
        public int group;
        public int child = INVALID_POSITION;
    }
}
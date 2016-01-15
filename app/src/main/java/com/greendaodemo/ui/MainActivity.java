package com.greendaodemo.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.greendaodemo.R;
import com.greendaodemo.base.BaseActivity;
import com.greendaodemo.presenter.impl.StaffPresenterImpl;
import com.greendaodemo.utils.SharedPreferencesTools;
import com.greendaodemo.utils.T;
import com.greendaodemo.utils.logger.Logger;
import com.greendaodemo.view.StaffView;
import com.socks.greendao.StaffTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements StaffView {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_IS_FIRST = "tag_is_first";
    @Bind(R.id.et_staff_num)
    EditText mStaffNumInput;
    @Bind(R.id.et_staff_name)
    EditText mStaffNameInput;
    @Bind(R.id.et_staff_ege)
    EditText mStaffAgeInput;
    @Bind(R.id.et_staff_motto)
    EditText mStaffMottoInput;
    @Bind(R.id.btn_insert)
    Button mInsertButton;
    @Bind(R.id.btn_search)
    Button mSearchButton;
    @Bind(R.id.recyler_staffs)
    RecyclerView mStaffsRecylerView;

    TextView mDialogCancel;
    TextView mDialogDelete;

    private StaffPresenterImpl staffPresenter;
    private List<StaffTable> allStaffs;
    private boolean isFirst;
    private SharedPreferencesTools spT;
    private StaffAdapter staffAdapter;
    private Dialog dialog;
    private StaffTable mCurrentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void initData() {
        spT = new SharedPreferencesTools(this);
        isFirst = spT.getBooleanFlag(TAG_IS_FIRST);
        staffPresenter = new StaffPresenterImpl(this, getApplicationContext());
        if (!isFirst) {
            // 第一次进入自动添加几条模拟数据
            for (int i = 0; i < 10; i++) {
                staffPresenter.insertStaff(new StaffTable(null, "有人@我" + i, "NO:" + i, 23 + i, "我就是有人@我" + i, System.currentTimeMillis()));
            }
            spT.saveBooleanFlag(TAG_IS_FIRST, true);
        }
        // 执行greendao的查询语句
        staffPresenter.queryAllStaffs();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        mInsertButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);

        mStaffsRecylerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insertStaff();
                break;
            case R.id.btn_search:
                queryStaff();
                break;
            case R.id.tv_dialog_cancle:
                // 取消
                dialog.dismiss();
                break;
            case R.id.tv_dialog_submit:
                // 删除当前员工
                staffPresenter.deleteStaffByID(mCurrentID.getId());
                break;
        }

    }

    /**
     * 只为演示,默认按照工号查,如果工号为空,按照姓名以此类推
     */
    private void queryStaff() {
        if (!TextUtils.isEmpty(mStaffNumInput.getText().toString())) {
            staffPresenter.queryStaffByNum(mStaffNumInput.getText().toString().trim());
            return;
        } else if (!TextUtils.isEmpty(mStaffNameInput.getText().toString())) {
            staffPresenter.queryStaffByName(mStaffNameInput.getText().toString().trim());
            return;
        } else if (!TextUtils.isEmpty(mStaffAgeInput.getText().toString())) {
            staffPresenter.queryStaffByAge(mStaffAgeInput.getText().toString().trim());
            return;
        } else {
            T.show(this, "请填写一个查询条件", 0);
        }
    }

    /**
     * 插入员工
     */
    private void insertStaff() {
        if (TextUtils.isEmpty(mStaffNumInput.getText().toString())) {
            T.show(this, "工号不能为空", 0);
        } else if (TextUtils.isEmpty(mStaffNameInput.getText().toString())) {
            T.show(this, "姓名不能为空", 0);
        } else if (TextUtils.isEmpty(mStaffAgeInput.getText().toString())) {
            T.show(this, "年龄不能为空", 0);
        } else if (TextUtils.isEmpty(mStaffMottoInput.getText().toString())) {
            T.show(this, "Motto不能为空", 0);
        } else {
            //执行greendao插入语句,ENTITY 年龄属性设计不合理应该为String ,凑乎看吧 (⊙﹏⊙)b
            staffPresenter.insertStaff(new StaffTable(null, mStaffNameInput.getText().toString(), mStaffNumInput.getText().toString(), Integer.parseInt(mStaffAgeInput.getText().toString()), mStaffMottoInput.getText().toString(), System.currentTimeMillis()));
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void bindQueryAllStaffs(List<StaffTable> queryStaffs) {
        Logger.d(queryStaffs.toString());

        allStaffs = queryStaffs;
        if (staffAdapter == null) {
            staffAdapter = new StaffAdapter();
        }
        staffAdapter.setStaffTables(allStaffs);
        staffAdapter.setmOnItemClickListener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mCurrentID = allStaffs.get(position);
                showDeleteStaffDialog();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mStaffsRecylerView.setAdapter(staffAdapter);
    }

    @Override
    public void addQueryStaffByName(List<StaffTable> queryStaffs) {
        notifyStaffTableData(queryStaffs);
    }


    @Override
    public void addQueryStaffByNum(List<StaffTable> queryStaffs) {
        notifyStaffTableData(queryStaffs);
    }

    @Override
    public void addQueryStaffByAge(List<StaffTable> queryStaffs) {
        notifyStaffTableData(queryStaffs);
    }

    @Override
    public void insertStaff(StaffTable insertStaff) {
        isFirst = spT.getBooleanFlag(TAG_IS_FIRST);
        if (!isFirst) {

        } else {
            T.show(MainActivity.this, "插入成功", 0);
            staffPresenter.queryAllStaffs();
        }
    }

    @Override
    public void deleteStaffByID() {
        T.show(MainActivity.this, "删除成功", 0);
        staffPresenter.queryAllStaffs();
        dialog.dismiss();
    }

    /**
     * 刷新recylerview
     *
     * @param queryStaffs
     */
    private void notifyStaffTableData(List<StaffTable> queryStaffs) {
        if (queryStaffs == null || queryStaffs.size() == 0) {
            T.show(MainActivity.this, "暂无此数据", 0);
        } else {
            allStaffs.clear();
            allStaffs.addAll(queryStaffs);
            staffAdapter.notifyDataSetChanged();
        }
    }


    /**
     * recylerview的点击事件回调
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    /**
     * staff适配器
     */
    class StaffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<StaffTable> staffTables;

        private OnItemClickLitener mOnItemClickListener;

        public void setStaffTables(List<StaffTable> staffTables) {
            this.staffTables = staffTables;
        }

        public void setmOnItemClickListener(OnItemClickLitener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            StaffVH staffVH = new StaffVH(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_staff, parent, false));
            return staffVH;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            StaffVH staffVH = (StaffVH) holder;
            StaffTable currentStaffTable = staffTables.get(position);
            Date date = new Date(currentStaffTable.getInsertTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            String insertTime = "InsertTime:" + format.format(date);

            staffVH.staffNum.setText(currentStaffTable.getStaffNum());
            staffVH.staffName.setText(currentStaffTable.getStaffName());
            staffVH.staffAge.setText(currentStaffTable.getStaffAge() + "");
            staffVH.staffMotto.setText(currentStaffTable.getMotto());
            staffVH.staffInsertTime.setText(insertTime);


            // 如果设置了回调，则设置点击事件
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                        return false;
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return staffTables.size();
        }

        class StaffVH extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item_num)
            TextView staffNum;
            @Bind(R.id.tv_item_name)
            TextView staffName;
            @Bind(R.id.tv_item_age)
            TextView staffAge;
            @Bind(R.id.tv_item_motto)
            TextView staffMotto;
            @Bind(R.id.tv_item_insert_time)
            TextView staffInsertTime;

            public StaffVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    /**
     * 显示删除员工的dialog
     */

    private void showDeleteStaffDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.myDialogTheme);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_create_newsonglist, null, false);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        mDialogCancel = (TextView) view.findViewById(R.id.tv_dialog_cancle);
        mDialogDelete = (TextView) view.findViewById(R.id.tv_dialog_submit);

        mDialogCancel.setOnClickListener(this);
        mDialogDelete.setOnClickListener(this);

        dialog.show();
    }
}

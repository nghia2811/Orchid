package com.project2.orchid.UserFragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project2.orchid.R;

public class InfoActivity extends AppCompatActivity {
    TextView thongtin;
    String info = "    Sản phẩm tâm huyết nửa đầu năm 2020. " +
            "Vui lòng không sao lưu ăn cắp dưới mọi hình thức. " +
            "Người dùng có thể xem thông tin chi tiết của các mặt hàng đang được bày bán, các mặt hàng nổi bật, nhiều người mua. Người dùng cũng có thể đăng mặt hàng lên để bán, cập nhật thuộc tính, mô tả của sản phẩm hoặc xóa sản phẩm trong kho của mình. Một cơ sở dữ liệu lưu trữ và cập nhật thông tin về những khách hàng. Thông báo tình trạng hàng đặt mua, giỏ hàng của khách. Diễn đàn trao đổi thông tin, các ý kiến khách hàng, các bài bình luận,.. Quản lý khách VIP, các dịch vụ khuyến mại dành cho khách hàng thân thiết ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        thongtin = findViewById(R.id.thongtinungdung);

        thongtin.setText(info);
    }
}

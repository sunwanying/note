package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.Advertisement;
import com.github.pagehelper.PageInfo;

public interface AdvertisementService {
    Advertisement listOne();

    PageInfo<Advertisement> listAll(int pageNum, int pageSize);

    int insert(Advertisement ad);
}

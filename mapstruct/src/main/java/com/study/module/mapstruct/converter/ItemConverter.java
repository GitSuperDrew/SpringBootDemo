package com.study.module.mapstruct.converter;

import com.study.module.mapstruct.dto.SkuDTO;
import com.study.module.mapstruct.entity.Item;
import com.study.module.mapstruct.entity.Sku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Administrator
 * @date 2020/7/26 上午 11:38
 */
@Mapper
public interface ItemConverter {
    ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);

    @Mappings({
            @Mapping(source = "sku.id", target = "skuId"),
            @Mapping(source = "sku.code", target = "skuCode"),
            @Mapping(source = "sku.price", target = "skuPrice"),
            @Mapping(source = "item.id", target = "itemId"),
            @Mapping(source = "item.title", target = "itemName")
    })
    SkuDTO domain2dto(Item item, Sku sku);
}

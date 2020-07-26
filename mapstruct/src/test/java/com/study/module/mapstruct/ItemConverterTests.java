package com.study.module.mapstruct;

import com.study.module.mapstruct.converter.ItemConverter;
import com.study.module.mapstruct.dto.SkuDTO;
import com.study.module.mapstruct.entity.Item;
import com.study.module.mapstruct.entity.Sku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Administrator
 * @date 2020/7/26 上午 11:40
 */
public class ItemConverterTests {
    @Test
    public void test() {
        Item item = new Item(1L, "iPhone X");
        Sku sku = new Sku(2L, "phone12345", 1000000);
        SkuDTO skuDTO = ItemConverter.INSTANCE.domain2dto(item, sku);
        System.out.println(skuDTO);
        assertNotNull(skuDTO);
        assertEquals(skuDTO.getSkuId(), sku.getId());
        assertEquals(skuDTO.getSkuCode(), sku.getCode());
        assertEquals(skuDTO.getSkuPrice(), sku.getPrice());
        assertEquals(skuDTO.getItemId(), item.getId());
        assertEquals(skuDTO.getItemName(), item.getTitle());
    }
}

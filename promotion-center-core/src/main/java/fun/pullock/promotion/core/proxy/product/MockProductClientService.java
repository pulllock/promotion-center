package fun.pullock.promotion.core.proxy.product;

import fun.pullock.promotion.core.proxy.product.model.MockProductDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockProductClientService implements ProductClientService {

    @Override
    public MockProductDTO queryBySpu(Long spuId) {
        MockProductDTO.MockProductSkuDTO sku1 = new MockProductDTO.MockProductSkuDTO();
        sku1.setId(1L);
        sku1.setSpuId(1L);

        MockProductDTO.MockProductSkuDTO sku2 = new MockProductDTO.MockProductSkuDTO();
        sku2.setId(2L);
        sku2.setSpuId(1L);

        List<MockProductDTO.MockProductSkuDTO> skus = new ArrayList<>();
        skus.add(sku1);
        skus.add(sku2);

        MockProductDTO.MockProductCategoryDTO category = new MockProductDTO.MockProductCategoryDTO();
        category.setId(1L);

        List<MockProductDTO.MockProductCategoryDTO> categories = new ArrayList<>();
        categories.add(category);
        MockProductDTO product = new MockProductDTO();
        product.setSkus(skus);
        product.setSellerId(1L);
        product.setCategories(categories);
        return product;
    }
}

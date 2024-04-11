package fun.pullock.promotion.core.proxy.product;

import fun.pullock.promotion.core.proxy.product.model.MockProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// @FeignClient(value = "${rpc.product-center.name}", url = "${rpc.product-center.url}")
public interface ProductClientService {

    @GetMapping("/rpc/product/bySpu")
    MockProductDTO queryBySpu(Long spuId);
}

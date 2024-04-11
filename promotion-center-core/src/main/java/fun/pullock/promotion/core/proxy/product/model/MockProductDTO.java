package fun.pullock.promotion.core.proxy.product.model;

import lombok.Data;

import java.util.List;

@Data
public class MockProductDTO {

    private Long sellerId;

    private Long id;

    private List<MockProductSkuDTO> skus;

    private List<MockProductCategoryDTO> categories;

    public static class MockProductSkuDTO {

        private Long id;

        private Long spuId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSpuId() {
            return spuId;
        }

        public void setSpuId(Long spuId) {
            this.spuId = spuId;
        }

        @Override
        public String toString() {
            return "MockProductSkuDTO{" +
                    "id=" + id +
                    ", spuId=" + spuId +
                    '}';
        }
    }

    public static class MockProductCategoryDTO {

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "MockProductCategoryDTO{" +
                    "id=" + id +
                    '}';
        }
    }
}

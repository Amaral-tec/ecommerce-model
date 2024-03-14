package br.com.amaral.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
@SequenceGenerator(name = "seq_product", sequenceName = "seq_product", allocationSize = 1, initialValue = 1)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product")
	private Long id;
	
	@Size(min = 10)
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@NotBlank
	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String description;
		
	@NotNull
	@Column(nullable = false)
	private Double width;
	
	@NotNull
	@Column(nullable = false)
	private Double height;
	
	@NotNull
	@Column(nullable = false)
	private Double length;
	
	@NotNull
	@Column(nullable = false)
	private Double weight;

	@NotNull
	@Column(name = "purchase_price", nullable = false)
	private BigDecimal purchasePrice = BigDecimal.ZERO;
	
	@NotNull
	@Column(name = "selling_price", nullable = false)
	private BigDecimal sellingPrice = BigDecimal.ZERO;

	@NotNull
	@Column(nullable = false)
	private Integer quantity = 0;

	@Column(name = "low_stock_alert")
	private Integer lowStockAlert = 0;
	
	@Column(name = "number_clicks")
	private Integer numberClicks = 0;

	@Column(name = "youtube_link")
	private String youtubeLink;

	@Column(name = "is_alerted")
	private Boolean isAlerted = Boolean.FALSE;
	
	@Column(name = "is_active")
	private Boolean isActive = Boolean.TRUE;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;
	
	@OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductImage> images = new ArrayList<>();
	
	@ManyToOne(targetEntity = LegalEntity.class)
	@JoinColumn(name = "legal_entity_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "legal_entity_fk"))
	private LegalEntity legalEntity;
	
	@ManyToOne(targetEntity = ProductCategory.class)
	@JoinColumn(name = "product_category_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_category_fk"))
	private ProductCategory productCategory;
	
	@ManyToOne(targetEntity = ProductBrand.class)
	@JoinColumn(name = "product_brand_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_brand_fk"))
	private ProductBrand productBrand;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getLowStockAlert() {
		return lowStockAlert;
	}

	public void setLowStockAlert(Integer lowStockAlert) {
		this.lowStockAlert = lowStockAlert;
	}

	public Integer getNumberClicks() {
		return numberClicks;
	}

	public void setNumberClicks(Integer numberClicks) {
		this.numberClicks = numberClicks;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public Boolean getIsAlerted() {
		return isAlerted;
	}

	public void setIsAlerted(Boolean isAlerted) {
		this.isAlerted = isAlerted;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ProductImage> getImages() {
		return images;
	}

	public void setImages(List<ProductImage> images) {
		this.images = images;
	}

	public LegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductBrand getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
}

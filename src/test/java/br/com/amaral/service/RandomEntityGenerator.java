package br.com.amaral.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.amaral.enums.AccountPayableStatus;
import br.com.amaral.enums.TypePerson;
import br.com.amaral.model.Access;
import br.com.amaral.model.AccountPayable;
import br.com.amaral.model.DiscountCoupon;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.model.PaymentMethod;
import br.com.amaral.model.Product;
import br.com.amaral.model.ProductBrand;
import br.com.amaral.model.ProductCategory;
import br.com.amaral.model.ProductImage;
import br.com.amaral.model.ProductReview;

@Service
public class RandomEntityGenerator {

	private static final Calendar calendar = Calendar.getInstance();
	private static final Date date = calendar.getTime();
	private static final BigDecimal value = new BigDecimal("100.0");
	private static final Double measurements = 16.0;
	private static final Integer quantity = 10;

	public static AccountPayable createAccountPayable() {

		AccountPayable accountPayable = new AccountPayable();
		accountPayable.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());
		accountPayable.setStatus(AccountPayableStatus.PAID);
		accountPayable.setInvoiceAmount(value);
		accountPayable.setInvoiceDueDate(date);
		accountPayable.setPaymentDate(date);
		accountPayable.setPaymentAmount(value);

		return accountPayable;
	}

	public static Access createAccess() {

		Access access = new Access();
		access.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return access;
	}

	public static DiscountCoupon createDiscountCoupon() {

		DiscountCoupon discountCoupon = new DiscountCoupon();
		discountCoupon.setCode("Test Name" + Calendar.getInstance().getTimeInMillis());
		discountCoupon.setDiscountAmount(value);
		discountCoupon.setDueDate(date);
		discountCoupon.setPercentage(false);
		discountCoupon.setExpired(false);

		return discountCoupon;
	}

	public static Individual generateIndividual() {

		String cpf = RandomCPFGenerator.generateNewCPF();

		Individual individual = new Individual();
		individual.setTypePerson(TypePerson.INDIVIDUAL);
		individual.setName("Test Name" + Calendar.getInstance().getTimeInMillis());
		individual.setEmail("email-test-" + Calendar.getInstance().getTimeInMillis() + "@test.com");
		individual.setPhone("45999795800");
		individual.setCreatedAt(date);
		individual.setCpf(cpf);
		individual.setDateBirth(date);

		return individual;
	}

	public static LegalEntity generateLegalEntity() {

		String cnpj = RandomCNPJGenerator.generateNewCNPJ();

		LegalEntity legalEntity = new LegalEntity();
		legalEntity.setTypePerson(TypePerson.LEGAL);
		legalEntity.setName("Test Name" + Calendar.getInstance().getTimeInMillis());
		legalEntity.setEmail("email-test-" + Calendar.getInstance().getTimeInMillis() + "@test.com");
		legalEntity.setPhone("45999795800");
		legalEntity.setCreatedAt(date);
		legalEntity.setCnpj(cnpj);
		legalEntity.setTradeName("Test Name" + Calendar.getInstance().getTimeInMillis());
		legalEntity.setStateTaxID("200200");
		legalEntity.setDistrictTaxID("1000100");

		return legalEntity;
	}

	public static PaymentMethod createPaymentMethod() {

		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return paymentMethod;
	}

	public static Product createProduct() {

		Product product = new Product();
		product.setName("Test Name" + Calendar.getInstance().getTimeInMillis());
		product.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());
		product.setWidth(measurements);
		product.setHeight(measurements);
		product.setLength(measurements);
		product.setWeight(measurements);
		product.setPurchasePrice(value);
		product.setSellingPrice(value);
		product.setQuantity(quantity);
		product.setLowStockAlert(quantity);
		product.setNumberClicks(quantity);
		product.setYoutubeLink("www.youtube.com");
		product.setIsAlerted(true);
		product.setIsActive(true);

		return product;
	}

	public static ProductBrand createProductBrand() {

		ProductBrand productBrand = new ProductBrand();
		productBrand.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return productBrand;
	}

	public static ProductCategory createProductCategory() {

		ProductCategory productCategory = new ProductCategory();
		productCategory.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());

		return productCategory;
	}

	public static ProductImage createProductImage() {

		ProductImage productImage = new ProductImage();
		productImage.setOriginalImage("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAUFBQkGCQkJCQwTDg4MDg4ZEg4SEBcOEBAXEBcXEBQXFBQaFxMTFxoUFxgjGBwZHxoYIRgjGCMiIiQnKCMXJzUBCQkFCQkJDAkJEyEXDhoiHAwYKSIoGiIkIiIZLCIoISIUIyIaHx8aGhwYHCIaJCQjDhkkIRcvGRoXIiEnHxckKv/CABEIAfQBNQMBIgACEQEDEQH/xAA0AAEAAQUBAQEAAAAAAAAAAAAABAMFBgcIAgEJAQEAAwEBAAAAAAAAAAAAAAAAAQIEBQP/2gAMAwEAAhADEAAAAOywAAAAAACkcT8zbV1p5aOn885w3dg6d0uOr80aZ0+VrScmlP0p/NHuTo8rbIt5gAAAAAAAAAAAAMTyzVkW/P20Zhaqe1DaWGa0q7Zz3mbOef1s71NtuyX8OYf0p/MP9C+jy9pC1AAAAAAAAAAAAAMc496s5bp6aVw66UVsnxLIdu+Pr82lElYOpkVLz5R5s+ltZdPlfo/m/wCU0vXi/VFwZ3J53nisgAAAAAAAAADWxzXp6xWS17tZvNwq2puvQXRHP6tehWh5dVbR2D2Tt8L169e9eIo1YTOpuWdqxbvwZfYAAAAAAAAABzb0lylaebceVvSKVOr99fCT05zBsbNr2fzxbok1e/tfRl8/HyXhRqVmRkuOyZfqe8+snuAAAAAAAAAA447H4ivHP1SrF9/Hwj/CbUg15imrRk1atCqePdGjCrWoySRO8WiX6weoE/J7gAAAAAAAAAPzr/RTT1n5/TJdvvFt9T4np5UfdT5MUvXn7E/fNT4fPXmkVPuSdCZdmiNu7L2fS+e1T0zAAAAAAAAAANY7O5z8dHNmLXmxvejb7jbuhyXj3SPlOv8AE03vwj7Rk7hrO57Hs/WnJ7WU7N1ltKIzob+YAAAAAAAAAA5J6M5tyb+eYf2vp84NvmxdnNpSfFSYpU6vgppFOJ+ducS/qR531nrjZOA8vs3LZmtthUnZY6PJAAAAAAAAAWabovw0wNX5xpfH0sDo1rJ2eHMgUJN/KtR+/Jj3SqRkz6f3yiR35wD0dW3QepupOQeX1s1yHHWXX0YOtwwAAAAAAAFuraG8dFS0MY5/UtuibzgfT5v315g68FaR48nr14+oqwa0VN28/Ptq1shxqWfqtzFuOx4duuJ+IZjy+zvW8YtlPX4QTUAAAAABC86Q8dFOzeLRh6kSDt7lXdzcCsWQ2Xdg+0aE0+/fEmYi+qXuJRJ1sib3Gk0bVlSPHi1en+vPzZ/SfP7cUZ/fML5Xb3vnWs9mbeYHr4gAAAAImP698dFLEPc7J0bVv2/cibuZddGbC17ozQ9f3eyWj5W+RyTMpebVgy4lyiY0C4WyJyKPWjXpL++qUxV/RL86ul/P0604/wC09DYN943Dz50FW30asQAAAHzUV40Bm6OX4hjuVeG649M4JkOrl6i5KyyzbcFinU8wNVw51G1fMGTHibhQq+JiBc7Pd4mnbZtvib7Glx70keK0cr3C2e0/q9q3GXP3x91afyTNr2oN/LAAAA0hi+48Ey9TG7hsT3Nco43uGB7eX6suz9VzXI9dZ/jMsKoyrReq6Qpxb63yNExLpaLhE0KcyDC/UfMz0p6oyI1oUPvmlt/dGcJfoLj26vvudYDj6O7rlTqbuQEwAAA+fQ5c29xDesWlcLf6UyPHLFcy1ysohSwWPXoI+yY0i0UfHrxE2i92e8VmPEmRidL8Vr19RqsMevFWJs36M/nP1pn0bklXLZefV9HvjAAAAfPvNExpPBKmx/WmEWrOMFmJFtyO9yhY3muKwwn7Vo3q9UqcTWoVqRbrxa7lE0aEigT3utavqBPt5WeqRA3xoet5en65sTyzyuAAAABhv5+ZjR9vOw2uxx7RkmeYtQrNXIcbslouMO7xZjEaVGXMe4UmKfK1P3E26tRrVn5Qn0CTcrfN9KocyGh5jSq2+0bn4mO0uj/zt/RLP7BWQAAGkM/4fvXD7deHv5Qp2M5rW2Q64qVS7Wu23EvN1ra6LZ7807V9qniYR5FCJt06HOpbzTkUZiVWhybRIhe/ZFqeKETd4tfxMV/1Q/Kjr7yv1OPL0AAA5u5n/SmFevBGt9maa9fO6ZrrzJiZFz7Gq2gbV036tE+HL+Wri1rzjL4nT3nLbZMWWNPiytsqjWpMmBMpS9TZnmYhe63u0QKddBKi1yvllkixP6n/AHU22cvuAAABj/IXbqX5W7F/QrTN68f2eBNvS845Dr+lcovWJ5JWbVYclsMxfY1eZE69pS6HpW3fKnrzsk/YJsTG7ZsEo47d8jNXU7hEvRUVJXWH92JS2e9m/mv+jXh6zhSwAAAAFPSm70uO9ffoKtX8nLv+n+BWr+eFPvLDL15Dq7Kxy8Y368RbRLtf2HWbhDrZvScGyW42m0Xe+QtiVtpS2dU0Ity786Om5dvM3zpOhWcV7Dzh74wrIAAAAAAAAAHPGl99aN5/YuEK7XCvptDOrHtDoca03KotQBb7gTyV9zHGeX2/MG+zfO+xNl83dI9LkB7ZwAAAAAAAAANQ8/8ATfNmDrVpsKX4adxbj0/uDo8cPXwAAxjnfq/QGXdj8qFVw9Gn0RozMdOTbY38sAAAAAAAAADFeYOxeRsXSiyPfrLt3LtXWuyunxg9PEABarqieWa+09S83s3j5Hr+fp0f9xzI+twgmAAAAAAAAAHL/UGi82zA/sz7g6m683xbKerwwv5gAANB78i+ftzXdrXK5nYzDcXOnRe/mBoyAAAAAAAAANW7Sxbz9tCVqXvldvoa9QpvX4AWqAAABhGk+o9DZd8Pa2qL/wCHvud8+9HkgAAAAAAAAPn2xJ589YBP5fZ7Hr4fmHT4wTAAAADAM/w+Lc5WnMco53VyXaug9+buaHp5AAAAAAAALJe4MW5zkxffK7eabk576E6PID18QAAAHz6Od6t9sHM7OPdTcuby9s2YjbzwAAAAAAAEWViVb6P9U/HL7ewNxaS3bv5Ye+UAAAADXmt9s6r5/UsmwcKk+Pv0YputxKgAAAAAAAGqdrcxefrjVLMYuHp1OnOdeiteIPfIAAAABa+YOs+ac+rV1bZcPPrvjI27mbjFqgAAAAAALSGobcJ2TnggAAAAABYxOuxDcAmP/8QAMhAAAgEDAgQEBwACAwADAAAAAQIDAAQRBRIQEyExIjBAQQYUICMyM1FCYSQ0cRVDUv/aAAgBAQABCAL0Grz73ephirC53ouZ5ftmoLhnyAzrGPGsvNGQMJHcbok2Ou5FXunrGYKCxlbmMz1L4smtNcEBaZSy+CIxyfuEqjpbCMIqqNQl/wCNPVmDNKq1o7snNgf1mpf9eXEknhIpmxSKVSUi01ISdG5KS9TGiIMCWTedi6jkW5qwmEUyMYVPOmf1uq3G1eVU6hWIEjBe6J/xJzUX5VbyMvZDJJ3RNo6NBzoFjae3ktH8Wi3XOhTPq7y45CM9Tz8v7jHYo8Q6nNM+LaRKt4yxq2j96UYFDtQGKeNZBtaCMabuZYdWtZewYN29Tqi5hrUjFNdkUAufE8vsAvN7W1sE7wxYWj/K3Vk9zcatI5+2zljlqQlOq2uuXMPe3nWeNZF9PqmuPKWSKX/KuXvyaaMr30nIV6WjQFTXCQLua71MzqUH0KcV8NzYeaL0+sXHy9u+JfBXWtppizd9MfbJsoCsUfCCzTzmZtx+lRmtHk5d1D6f4jf9EVSbN5au9HgkhjIZLC5+ZU5vbwW3hqa5km/KhwzQ427FZY2X03xEcSxmmOa/nEVp86wCZjI5kZmP1E0KVyhDKDnB9N8THE0dCv5THgDXf6j04AFjgUib8ClHQem137lw2P7RruK78Nx+jPE0q5pv4W6irdg0cZX0uolufMSr4OGkGDn6s8e/DOKQn2FlM1WGhfNLveNAgCj0utWKXKbjJZkE1t2jrjyorXKcw2vhHQ1pX629PqkmBipjQ65pvK1GzFpaQIIKNaX+tvT6tJk1MaT3o8cfVoVp8xcKa+IP0pUHajWl/rb0x6VqB61MaTtRo/Tjj8NQbInkrX/1xCoe3DTDgSD015MEXFX/AHqagvQVnP05o9OHWrGMRQRKuvD7cVRjhp/5SD0txPyhTyddxuWrG9qmf+fiKHAe9HgOOgXXOtwp1tN1vmoj04WjbZV9JLMIhkyymQ5Mhq7fPQMwQYH5GieB/tKMZo01Cl7Dh8OT7J2SpohKjIYsp4StM23DejmmEQzUspkOTVxLUsvU01HoMcT2oU1MO1Cl/HhazfLzRycNQTk3JpDT9qtm3Rxn0MkgjGTNMZTnhLJirqf/ABApe+aPXifahRr3rsKTsK9uGjzc62iNa3BuiEgjfNdxWnn7KegkkEYyZpjKcmpJP5cRm3XLSx7WYUf9N06fR717CjQ717UnaiMcPhm6Id4aI3DBCGB3iMZyK0s/bYefJKIxkzSmQ5JOKG6Y7UKR6YnMaa5EnV+Udm+h/eA4YoUg6UaXvR7Go/8AGjw065FrOkprXIMFJxbtmtN6NIPOeQIMmaXf1LPUMD3JwsUMdolairykSkSRospjlCLFtVq/nBaxTGgBTUvej+NR17cD1rR7jnW0ebi3FxG8bWu4Nta3bZKvmZp7oDs82e7Nuq3tTOaRAgCrrF4123Kj5JuCkUXNaKbpcspduXQHvROKXg3bge1J3r2NJwzw+GrrDyQ8L6Dbch6lOMERPvVW8o1cXJNNNRl3dkycKIIuUirWtajtzbou3oSWI2MIx0w0hDHIrt14ZonFP7Cs0/akrOBS9OGOEMhhdZFilWVFdbt98uKlGas3DxJ5V3d9ComcuvhSwYY3cvZWnW20cw31ybeMsrWrnLydA6ipQ8cvj1EbNqO6bWK8GoDgRmj3HBiMUnShScf90K+HrvnQbK/zm4WL7HK+VdQLcT4I8Rq2tVbJqyhMrjdNMsKM7zX/AM7v5usScyZiDOI9hqN/lYm5k+NxrbRbFChR4HvWK9qjr2rGc8RTV8Lz7J3jq6j2TGmilUvKHO0AiCbmID5KWzCVnNpBzObi1hYROjWcXIVi99frfPgSmGPmK65umEjX0rTShXV1to1U3KBNmD/vjjhmu9N0FJSnvQ4qetN/qwuPl54pK1IdYsQDMUqnbkRikQIAB5esXnOb5dRvjYxrM3TYLaTDNLLGm/c8nMO5cheZjbJjJFAUP7xNd6HtT9qSlNLRPA9aPCznS5tYC9xNvzHHaRF3DeZqN8bYAJCjR7HESk+EdYOtFtx2l2fs8f2cvG6L0UO29mPDvw701dzwbsajof2hTGv/AA17cPh8JMOVUVh/+wMeWzBQSbm6+aYyvI772BkheLDqblt3NMdqX24ERBO4ZmDSMznx7+H9x34H+V78H7Go+3AisUaNLRrQXxKMeZreqAK0Kbx2B3Okzjm8pHJkDK25tvtQ+wuJJ53RFgqafeoPA0eg6CsV3o9+D/jUfalo0ema/wDaFPSd60+5+Zgjk8vUrz5SJnra5OXJTs8m1Dms88lnkXK9GUHCUGnYPcC4JGFeTAPh3YpeBocPejTg9aHah/eJFDt0pxQr4Zm8DxeXqmofNyMRBeyiJ4K3hc0gMjAAeEqqPIJixd8TMAjTFVMdW+6LfQO8ua7mhwP0d6boKWk/3w9q20Bx0a65FxH5Ws3xiXlR7PAWo5U0FQLk2yhwacnY6CBN7KKEJRC5jAwadA58NzFHboVo9K7cccDQNMaFCmNZrH8/94YpG6jdbzrMiuv1398tmm5pJHDK5OXAp9ka7QhKtSMm1whXd+XLKrzadNgBqL7mFrcID1Z93DOfoPAUR3oe1Cn70OnA0vAnh8M3X7IPI1XS7m6cuLjnw/alS7g5WypWXpiHaMlneLoaa3kTYGdSGMSo+xWWmh5ipzXD4yzOO1Sd/pIpaFHtQpeA49aU0o7ZZNhNWdx8rNFL5NxbpcLsk1PSGsvEKA6ZqON12sqRSwAB5tqTOY1bJBp53d2dubne7wxsHwksATIJ0wZKmSz2sq1LEEIxwFLRHQ0B2FciQHFbMrkKAetFccMcc+GitaHdc+2XPkXNslyhjk1D4bkj6wPbSQY5lnp0Vw3LN7bwwOY45D49rS7MeEYHey27wT9s7hR8bUXyxaoZuXgVcoAwx9HsaibYUanuhKiqyNjvbPskkAZCME4+hMYrbk4rQZ/l7lom8qRFkBVrzSN36Z4XtnKSGdO4BznLvuNJF1yUZvYMkWK5jePNtOqSrIbxEcc9Dxx1oeLpS1nLZqzG/mJUVxypGkSckqm4iscVYqQaEMX+Ek+1o5BDKsyK6+XLAkow8nw3at2b4ZlXO2bRLqHxV+NIN3hrNZzwcg13rB4NSKZCEXaV6GoZeUwapJV5u6O5uucXKuRWc1mjQBamG38otBupMVpto1nHyz581tHP+yTRLd8Cm+HYOmLz4ezjkS6PcRV8rJHhnZzW6mY8PavmI2U4VShUSKNzdDZTSHpZ6FMzfdHww3vcaEltg0LaMdtgo1aXHysweh19PrSfcU1gUFFNboetabYW7wjK6XbJ2NtGRgqgT8eM8PNRl4hKeAVpUu5OWfTawPGOA4aM323HkahFy5c0OHeraTkzKfT6sv4niK0c/t8i/g5sfRTwWpVzVtLzo0b02qL4AabgK0j/AOzyb635T9BQo9RWlyYLx+mvlzE1MOArSO0nk3EPOQrXY4NClflOr+mmXcjCiOGK0keF/K1C2z9wChTDIqxl3xD006bHYUBw0sfbPl3dvyG6ChWnvtkZPTaimJKHDTh9oeXLEJFKl0MTFSprdsKvQOfS6on4GhRqx/SnmX9tzV3BDXcVYSbowPS6gmYjQp6tf1p5t7ByXyEq1l5UnX0kq7lYUKkqHoi+bqziOHcU1C3Xu1+0/wD1tEUxxbG9HeXaWiF3/wDkd/6mkvHHTTDKYRzvMvk3cs0kMYbNSHfVpPyWGfR3uOWclia9qsbnlukR8yRNwxTDB4dPcHPo7lQ0bZaloLvVR516uJDQpqsH3RY9HcHCNRrNaf8An52pL+BpaatPk2tj0d6fBijS1ZNiQedqP4rS1ihStuAPotRuY48B2vrehe2/vbzA+NPNu496Grm9itOjrqE036EF9LsNaZPPCZI5vQyGK2mm3rOjdAuJn8Xyg3qyed8oLeSZqkhxmZXureJeZWnJ8yGmk9DcWcVyMSyfDFo3YfC1uO1vpqW53efPbLOOs2hrOMPb/D1pAc8P/8QAKREAAgIBAwQCAgIDAQAAAAAAAQIAEQMEITEQEiAwIjIFQRMjM0JRFP/aAAgBAgEBCAD0JyTMqduQifwjk4FvMKX3twYDMmBXU2RXOHIEYuVYEWPc0AqZdUEBUE2YATsNP+MdPk//AJ/+HGV59YFmpkUbKBhaarF2ZYFZiFXT/jkxAE1LjraH2KDeyYq3aavS/wAmKxh0SYVFAdLh+p9ifaCXLhgMuV+o3HsXIE3KalGPaKlS+ub8kMRqYvyT5iw9mdqSaRfn4k0Lmq+wmkPzPs1D7gDSJvcPhkOwE1P3E05rKPXm1QQUFDOwEx4hjQIO6fuHpkW1uapNw0R+11b1ZtSMYobk2dNouz5tfT9wmDmpViZsXchEIqKbUH0ZdUMYoUzmYNDXyacyuh5E/wBhKmRaaZ8Xa5mFrxL5vqgNlGIu0xaULBxUEuoOIBuYfsJ+x0yLYuajHaXNLktSvll1F/Fe6YcNCyKUULG0AhlmoDuYT8hDyJUoGxNSaQiad+168jhJItdPuhISuRvtF5hNRR0ErcSrqGXtNQloY2KghUA0L8VS95dmp2Tu/wCQQ8RRD9hAIW36ZFtTS4WLBn8Qtmh9YpndBcraX+oYs/3lWIZ+4wux5qhC3Klbyrgg3MbiDp2/KLxDXQpvccUfFEswGUZx0Ckzg9DBN7l7TmcGHiOti/EZSNopFWKE7rM/jJlUIXga4VuAQ8wHaCEQTt5EIrxDET+XaoP1BDuYBOG6VCNxAAFgJBlSoxqODd+dkQZmg1EGoWfyKYGELXCSCIGBBB7lG8bVqost+cTgL+Z7mCw5C3PrzOwyGl1OVfqmQsgPUixUyY+1iJcwZu/GCfXqBWTph/xL4anBY7xNLlpyvs1Q3BlzD/iXwIsUcuAo1RWKkMAwIBHq1SXjvpjFY18cmAZFqFSDR02S07fXlTuxsOi/UeWowX8xgy9uQX6jDpmBNrwPJgO02SinbFkLoCfS31MYbzCbxr55hTkTS5eU9TtSkxuZpj/X56kf2THkKOGl+nL9DO1b3wJSmvLPhU/Mjkdig9ov1AegADif/8QAKREAAQQBAwQCAQUBAAAAAAAAAQACAxEEEBIwEyAhMQVBMyIjMkJRBv/aAAgBAwEBCADgkH6QFDKJI2uXQcAKypagIT/rnb/IJwoqHJdG8bQ9zm0siIvYGNeCPB5owibUOEXODk1tClVGzkfIwvsN63+hwPLE6gSTKxYcofAKJAFnJ+VfK4hl6MPnkAFjdNkBxpiwsoslDTP8jJM4jWkPY5HetKVIIhVoPfJ0y+mtfiSMG516Vrj/ABRmbuOR8YyFrXDjxmXLazPxHQajyVhfiKzR+23kw4qBcc2SmbUEdWDzawq6RWW24Tx4+EZXbnHaxtqacyyFypV4Q0YaKwpBTmKSPfG5vFj4ZldZDQBQzM8OuJmn0gNLorHm2SA6ObTiOCDCMps/pY1ZHyZdbY70vQeivpWmHwsefqRgrIbUz++PBc4gvLmxMsz5rpEdSifCHor60YaNLEmLX7FmxU5r+7F+ONCR/R82svL6jtjTZKrQKhaPoIfxOl6YjbktZce6O+5mUwNJEvyPiYAu/wAPhFAInQ/SuwV61xH1K1NnLjIHkgkkdrjpuVaHRy/qiUBpEWh/6pcqNrDFD2k0vaIVa1oV/VWhoD997nAmkDpdaHwhrfhFDQO8Ummx2ufSKC96EgahFfSryvSpBNP12mO0QbpWqW8aBq20rpFD0iPOgRQd6PdS6aKKCJXsa2vZVAjUC008HTCMSMJWwqlSAsFbStrimYrnuDQ3/n3fb/gwxhcAwD1x44BhYnQMd/KRm2RzdQ4ggiOUPYHBZMHTlIHHiOuEaZP5n9mHk0TGVmQ7ow8ceE62ubpkfmf2BxBBEOSJWAhzA9paXMLSWniwn1IW6TG5X9sOSYn2g4OAIzIdsm4cUD9szDo8293diZVftOyod8RriBo2BmtIFOvcb7WEh7a2yuq54hHIWjhZW4W30FkNqZ3cDShfujaVmw+BIOGNtvaE30ssVN34jv2gpYuowtVVwwfkCBkIpuW47gH92NNJfSY6MAHqvI3HbxXfeCQbBcSbK//EAD4QAAEBBQMICQMCBgIDAAAAAAERAAIhMUESUWEQIjBAQnGBoQMgMlKRscHR8GLh8RNyM0NQU4KSBCOywuL/2gAIAQEACT8C1C/Jdkd9oM/G+XJojvULS/SebYstW6R12Qash75e1jEM8+twbowHjXtPDfc1A14HgjbTwi0+iI8HrsLrtdua9qtQhoPciwaDcWk8895/ZpB4cm+keEfXXSlqeDon4yGTw9274yFnskiPDFuBodzXTv8AxXXJO/INndI94L/808bm+fnJ/dd8ivWCi5lsTs1BF0gVubpBxhrYUB51RgDFnkdAERHg7i0Wk1/VgAxsO8yxU4xyQ3QY2xcfdpPDWCjnN5vx+WOSSjqcBU7mdQHietcD6HV5vQHFq5S0nvTLRvwLuvVR4jV9olqdQoQ3adm0Xrrt7PcJDw0Ew+6g1ejh59Y91Be0ydDQ79X7nrpp5JF4BdXPZT4NRkXRq0M4/Ex55OOlcPGDP2Y9lItIDVoPCR92OklbA5Lyy94/0TZfHiZ5e8dZv0UnIn0bvjL3jrN+i2z/AOLf3B65b9WmdLR0RvbvjyOXDVZtPSz6OHs2yXTlrrNOpf1tseTVDTd9MlDrlevsvDJthffLcNV45KaSYCeEGn0Z5Vy46j4Ze2adwGFp70a8x3aLHqVCtVtg8qZKPHUgpbP6Q9kY4NnPvLFYKYCFLDSBRdNIesMm4+mTDUZVeaF5vZ6yVzXLqr6vYcGEX82x2kBqC01AWcovIbhzXdp5iB4NtBpifBq6hITLBAG/hgzkCd/le3SEO/pgoe0BKS7c0ZH/ANO5Xwb53BuysMBQcNPtRHDJtO+UGo1Ro/n3OWZajGJ7R9OLCAmla8Fkz8bM4qKADgwzHJvOCP8AtjyaGF2Gnm6Wk8G2PWOll3+XwsWHq1ZfMWCvUG+DEAkmD5sPfuSbSBNl8uTOI9KBjnX+RDAuvGJUjOJrCHGrUOofyzyMm75ybWiCx5Bqqu4U9GUB0RixVJwRpBigEQ5IoLr3t7FTCFmLiD5uZ1YK6NkXvHvKflGzn/8AkJCpdN731XMC6aumKHDDy1Hbd5uttgezDMhW6ujxTi3dTiwRVaH2bsqgXsxrhx4RaMUveEFNy2XoWt7Pl0PAkdI66gzIHgBLfwaFkAKlmFC8GOJImsBZBpCNsTZxF9YhOGo0ejukWxbHm0y1NIQHVziTMiKQUtAvEQXw+xZ+06DXNeX09WWyBFxEcJ2XODGLxirtHtoezGSRuSTIFIFwC++WmnfR4YxV2Hze0nu09fu927LstI7afek7Pi2bZiKrjuZ7N2yQu9FziiM+HrbrwV15Z3j0Z5AsV9Q0UEKQxqjTzUBGdnhV4c2di72tpd3m1TTJXRU6sCi76N+dJINJ6gKPwgHR5nextEGBdw7uFwYKAV/VciB7EXFir72A3MQBBSSllb/kWdg6c6EmVHTmnu3BaAfhnVJ2ioOHjz1KYeFKHNejhdpYrAlfEMM1Xbi9/wCpO5kNqznJZAHR+PEGywCvPun6XYKFvURA8W7b1P3d7fcwLzqyJsx3yKX1BbNden9aRVJ7pcywIICPOJC8J+6rZxemTMRoa2uWp1EeENHPZ3s7F/6Vtr74MQUOKOxpeMIEeLHuwGeCl/rBhWTooZx5Os4tqFsTePkbodpFJYwipKjxErQok27KJFC9Zw3SN7TN8Xty7qUYrinyI1OkfGfho+yID34sTngWMMOLBS0zRs0uvTtLFyMR8HNhYhQIFFBdBim9HB4CAbsqqYiDFAhUZr8aQPmzokYSASMOGpyMD/l99F2iI4O/dpDzuXARaYxYhXin7bya7psBMKpp6KYF7He1JAiLnfdBovGAaKnsqwBtVt9ng06E0LseeLAAXjyd2qyijfxHuNkCEDKOqFV0E6C9irzz1q1VRKdBuZUdmVzRajD5FkeJT/sEtwa44TgxeH+ebdJASOYaYpX4Awg9AIUjum1ae7HtEcpH4jAiwiAiy8d90Yt8x46pv99AhAkFQsCBaVDU76tbECO86FmaHg1Koit3SkLSn2azIQiS7CytFvPDFlAfQWi75tU3qT3QcWKqf4di1xjVWsvPdInZNkuJeYo6BODLZJEwUIEkM4Dk0gtldkGqXt7aj+MlDHyOhChouEzqN+T4jQtdkqmCLTjNnkIfCRBsVJCvWfdoh0LQuxmt+6hYpZ7IdokfDGLFS9PGjPLapN7DAM8j9Ct+NPgaCDtX8McWBD0MxQbOLxo7zY9oCK2gv+K/hpEAjy5GHXdKiicZMaiFWKdb8bmDTch4S5aEKC2eLtr2Z0uLeEVumsvxV2fgZFnrSV9LijFQoUg2sJ1shhZO/NhdUq3swtpsUe+1S3Rdk9pzO3zmgkxWBUiNoDCEPZoTgt/2gxSE3QFFYHGrHtg1XrBUSBkWgj1Kgyj9Ehg1We/TtrRdwv4s6nVxXjLmxExH5Rv5g5jRhQaFrCJ2HxmbwkVaB8+LOWYStQ4b+OT4kGzUAPePAV9GM6WQ6IXbrsWCFO6vnArNTKTbSKsT7ssPPlBj233vSeOgKQX/AEj5MS7Ccz8JbNtARMSo2cBtJj1rQV1RaKIRGNCDIFjnOpHaKSteTSeGkdDwxCsru4s86eS+zOyqCqeuQpDf5L1AnVm9LFcoXBnbIKQNL2KW0zRKFDeWj1nbKiZPws/a4ImGoOg8GEheyw4hrMKFVO8t0ZOIi3Rkb3eTQ5dV2P1Zy8UBtKziB92EL4gi9r6CPg3Rvla2T4xZ2y6QYqPJukH+v3Yl5WdGWVdYqMgZ0eDdE6Z0box8izg8FYJoZueWr3dSj2g2upWB463hoJjW7+phoZHqbx66tTqYaWh1a7qX6Lj1KQ8NWvy3nR9k5doeWrVGXHSUyUP9Fm7l2YarTLcNLJ7JJ74NVqMtw0vZUKbm6Qcy3Rk/W9B0M9aIPDhqkm6F97l7t0Trm8qfnBiC9hpdl5eRA5t0bq/tGTaeDu5Zc4aoFlll0geTe79tQl8OqXZdnpeiI8U8tRpqd2o45dr+iX6s+HcFj4N0g8D7N0nI+zEPAES05zu6J8bm6Di8fwGfdcC3SZ4dJ2SCqTX21Ih3pIm09Mh6Vk7oJg3TiGIZ4PHo6TmwQqIgIqzBvUacZ56Qm1g9EIxiBnLJ4C/G4t0gIeSAifBgjryWAZ2RXjqTgeZRuP5Z9/xHszzz5Ei+9aTdp4HvCBDdN0hFyhPJnbX7o/bJ/8QAKRAAAgIBAgUDBQEBAAAAAAAAAREAITFBUWFxgZHwobHBEDBA0eHxIP/aAAgBAQABPyH8AoLt+gFSpN1k9KMoRkioBT0OIbv5B4CG5aRmLkQMbid12m+xnWh3gjiTGTyRMDFQF+It7X+aZlAJJ2AuAK0OYAbB4jAIpoekPU3dbH+j2hYwACgDOnHSCgUGawDwoV6xexdzs2R+RmBAQgSvUbrKu8Is4eohn4NwMOkwbAAw3Vawo4/NcA3e2sOQN/b+zA0C+lSo43dQX8QWPEO3LtAUufeLQOSpsZr+OsZ/8KoyCCetn0hxQor4qS9V6fmgADBJPrnmJMFg0LDwX5WkKtHtGum5Db0EQLdRPCs9YaCZCEyAu0FW0guOyrIEkB1J5gwQAXo6GpfGRrE42R0IruVd2o/MctAZ1J2AYZNM4hGuzGzAFYIxHUstEsDZyS9tBxPXrBcVDhN9iXNnZcK9JyTGgQOAvomHCkY+rH8PEXBJhGbCMYzNBRJE1cWLkjBP66wQyBG4L/KctuiDJEPouc8VUCDqIYHOBMhJHbEtQgOOUdNlZJwG8jn9SqPGGkOs4YQAlABJJ0AuEw6QOcTaPLEchqEiXczNGFAQloSgiB3ZPLJnrDytjluDxBo/kaRbY696DlmCQQwWxp1bCLgCN01/pmMEc6hiVZHE6wHCcVGpzgy8HzpBwFnb9c49I4SswUUY945tm9tI0dQRpXvV+OUokvn/AMMwkG+HAS4SnBmuXqe8KQLEe+r4hYVhg4oCT0hfr4GmkHl/RRDyowX5UweUEaJUagaM3oDv+Oer4OA+jMYSGNHt5wiziOEOO0HK30WvGMwAES4LsEDTjFwB/SB1/TMykR6HYKgEtPP1EBvsoWpgrFQQYr2BNin+O1g6gGSSRfBQmijASBwD+QjImZTjBJmtzfT9QobKJ6zEMHmsxrM5mUPKhGGN6g6ytwiIKyFisRfcA/GZvFoYHJ++JgLtFGv8hNzREWqGivodJ79o1iUbvpAXAoFsAWTCQgjjy/aHIxTVGP8AMmCAQWABeX+MQ0SpknFABbJrZHEAB4Srx5tCecjL44P7h6oc0KMjz3mp6Ql/v9wZjBR+gH/f1K0rhtwlKMIRrHSEWyxnXO0sLbE0SDJyiK/GIF3UwqwOKjjYQ5Ob3/csGRG+s5TE4j9Rr6KhvlHACz2huMxte18pfDo/nmGahcEGrxwHyMpdCAHAUPxg4mlQ3w3D2jIE9JgFj28qUzpnl/It4LqHfzlMR9vooTp9DCxefLH43mBTlUxh+VoPx1fBEyvp/cuXoCOfP6Jx5M1uHYfRTgf8iOgfzL9rB3ASXeBQmMr5WPx2I4/SxLg9oTXE35tH2MfX3+jGY3Gx8w7esEKWF/WGPf2lfLowKTGeS2H4xZbRjb/Q3dfYOsLzSHKqL6LvNkNVNY/2/Tp7ky3kFAKMzkJ9R+NY+yIeOwh3FD1PzCDLAOeW0rWArEBsjb5hiShzlCABKhZgRTYpnrmXUioOAIw+eB+4/FDVlgfuGcj3SkkzoB4BMo/U3DQNkP3C19IBe8u+5njf9TUHzpLVoXASU9hBc1pXZn010jz/AHi+Y0IJydI+R7fielTeEP8AhKIQtY4gQRv1gyKszCmYQ7QkDsHNaRAD40g6i3KLrXKXJJ85TK2m/njgjeK+d/ZzAoYd4DMwQfRCjItI9oC/wnF0G8JpEqJoRghnfTp+4Upax3PWAvTnBZSdnEHDcMQaIZ1lh25T+SGF5tMHtMQu4E8tfSoC5wyIOeP26/RFlynjkq/BLcfvCEu20ai0MCcmrbh+4mcQmXS56dszjwXiCs+KGzpyeU4c8cwypgdrh2Mv2qdoI31f2G37QpQth5bQgzldVfv0igMJi/hL6n8B/wCttTyna5oH00bfMousIzRnrTT9GHyKHqB1EG365mfoEMCALXvrN1wh2/yHA18cCW+sBIhqmHl2h0iq/wBTBXglLxxwh8K8xR7j2gSAYAsbuZU088j6iUkb/oF/f9KOpnbaNoOE7x6cTsIEAww5JaD8/wCQwBAUiV9YaKOG7xVxZKVA0OzlvqiDBWxY5wT2ek+dIQKPhm0+OUCnSO4g0xpr5tLxbhNwj0PlBQ81mmIAy0vkDdm/p51zZ9DXX6AuMt8fecjpDEe1tE0O8wGDJgfs8ITKmrnm+BDt6aWoqw5VYLhAcxYBA5nFt0BsncL0FsdmBogqgBuMmCZQNCEKK66/0y5JEbbHUwt8olvxm9liGyhoj4XMF6T/AAOUCsf53gEBxhzi/jR6TFQQctj0Nx3UYgONDOU5B9x7fcICzMacAbO4QyoZjHWeA8qaPACNUAWHIDh2VegF0j2IYNjCqEVkCgCCdIShI1QCR1a2gwkqiNFiQ56hhNZcObjKA78ZrJqCs1KnuTF6NHUeesPV2/UyQ7HSYg+HX+zZecogY8MBjuOKOOS5j2+gB3H5/oU3qQ9poYg9/tEgzGGmw2G59AaCJbMfOfHSDkODiTB7Ss7nWBCiuQ+0HUdBzENmYl1ggnFHIATH6gdrqFlaNIAjbgoJOwNz1yUMwpAhxmL0xyt5xhNPB/ycOx83i8l3iUoO9OCCG0LmZ6Q8SR2UJYc+aw4ab/Ci888EpdAIPLTrjrChMAjkbgDbC62eiiSh0GgAI2Ir7QRSbk0oKOjF6BxHRwBsbCz8wq2Dag56iIB2FdgdfGOswOYb5IB0ZgJl0uPJ0IE7PGDAGlDrAJGgzeTbsOW8UsGSGcuBom8DpCCFpRjybGrtfJLqBcKsFVR15wd5zOF07c4DV47nnMWGEDDjKs3zhR8dIfshUVfKIiOb1rhFgnxQ1Y4ePEJLpzEQRoeV+rDcNg1NhQrmP59rPsAPawqE+gm8GmGMYRzu5AUeQtlwimxFh2BnX4hkkNnzjpLGmiFZwwbAJthoLoGveYtmbCDJOpAwCQRAFggoYYvKzaWkBMYIg/c4rUFmJTcaRaakFB3DCiMQLDO23giFk79TK2dbM3vJhB1088EVYhf+GEA8UybXGCyOwpesCEyqh/34mRNB86wCqhjhqob2D0JjDoR1FviOKoGA2FM18TWFoRpDGwjr0qvsqUA5r3SgLW+gL/JjEYPkvN4Qd4ySMBnYCDh6hhkaueB6kLDQIvwQpZYsMSJIkFIBb5WGaZe7bV4Uyomdd3iYLKFbRA1k5wBLaIiyjEOCwCttmxuJoEiSyS9oTvV2r+TUrebDeFD4hInwRWTsJkDFlrtAb54prmJBIqGz7QXADmjCt3tLdQE3F8BhycCbGukY5Vc6LryjaqAdTMBQfcXh3NAo8IAakjPIy/o5WM63RovqCcKIIpK6ZKGzqGu1Bb1biyTqADJEVhKaN4QI2WkhkHZixLMoKg6OyOi7OEYFIZeFkmsj14RQjQJWuOOIjnDZTRD4DM+VC1EbRre1EDnGFwvaC3Cko9pTgPO0EOmRz95QKzCosxniHWDbCCzzE8sWYQOLMeGo4Tj0Q5xTyE4rlv8AcMexgEkMkBoMaZjtMFoBnxYsckoEb1GLBilqQSQBQIsIhFZgBBdE0QAYmtQQBfES4xNUQ9SCyRvmDAOEd9wF4mlBmhjSGRr0gziAbB0CqFwMap2ZKsgQBkCNMmocjTjRC9tuUVdO8AKDT2CZPlRnV8S2evGNEYz6+sr0vWYTJFjzrN47z1kwYs110llPaZjcpNhlC2+GL1a0hVfWwDfMYgh9s6aASSaAAzEfB24C9EEZNeQyxDgRJuqCqpoV6Tt7HkQFW8QdFDUWYSQYzCWOUfCgEHFiAM4JyMkL3jkKZNwI7cNzBKYpWljY1RNk6YyM0R4xMEXAobrBhWevhhsVKLeHSE4MCHc+fyW4tIqKBVc3W0I4Swl/kMwf5iHDzwQs7edIXHzzWFffpKVxxCZsukZGSFWqmwHX7qFxfPA3QEWKN0bG8eCKAMSQENQyATow24y34NZmdpVoAtMIGoXyCIDJrukzXrvQo6dgLAFhr0DkWC6qAbK005MAcQ+oHBEKkIKCwCQTKwwByWjIaYMJClGSycuZR2gBUzWTwCzJZRVUFS86R6f7ABGOZahy3jGHZfMpaC+sKz1X0CTWt+amAwXV4gPs9f8ABCzzaxVem+RyMMPzBgKbxQOThAxl57iz1H2w8tBueOgyeUQmFZAJWHRF81toCHAowA2L1H1LDULrCGCE9ZgchrjeOSMcUAuFksBa5Q73RF9DIB0+c1SGtzvxIBd50BEmHGESNWEBVCEQVgGXONTUCSQHoLMUILsuBq8jqA7HfgUYv8QiQ8OEnTtALJ08tQqi09JwQ6DP8gV7O8GkEsAP3DZx5gwaShaa+0EEthyYLOesbeJWzAHfAB3xpxBfaJU/U3XfV1CoI6xx9MAdBw1rWemJpFmS6XXXUEGDmZkPYBj+wishDhljBYJZQN6BqjK1aLaIQxJ8bvUw3WLfylWTSTvUuMjqtrMtV2JhCYRUDKFnCyfVD2g4A82z0si3GzaExvOcw/WssUdJwhy3C6b4lkcIaAlEw+YW/DCQM/zwwEEZgecQUH57TPWALzpDEA23bIPh9rOp6rA8k+iDaTBVq0brGw4mAWHZkNY1BFHmI0/WWeE6sExa3Lzk+vkFVBsUFAwKPNAxJRRW0WgcjUwo9HVqE4egfxMNPhTLWhdasJKPlZ6DAACjpohK5NMFM5AbYguRapRAnuUMJ6grtQBWk6r9YECHeGPpC0dX/RDZcAOA/e0W9aMMprA+kPnaoAaH85QmHogeuttZ+8OZziHJmojMRBxka7aYgHgDkeemn2CW4uefmTBZBlHBVbpAVTQgZE0EtEeQ9cwJosuCgGREA9T+pSKJA9g+hZgII8hPcTNN8BxmEYmUb6AaUQBg4RKGUUBKEcj7W9HAFiCdLQDxWmsI45Q40AOKS/1cAl/pxiwBCFO8pXU2l07JJOWJfZALY/fjgJvFHv55rGqhHQzxxgzKPwTMFUX/AJpKCKZ06+kuDmcGHLKY89IBzrvvCGpg80lYPggJKAyLZlCBVdy6fTbb7BKsRkAdQmTES1aHBJ5XEx6TUgx/ognWFAb6ru12fiAYojYQJSINc2QcREYIkJEWkrAC5YJFjaICXcLB7iRla6Q5protxSAVsbNGAEPGG5CEK2BkhHSOh25RYoCgIJ3msQQ1k41CCW0eLpEHJNTrDYKJDjkcpmWw0p5v3j1Hm83XpDWnnmsccoCJgQS2iEZAQj2hChWRt9P1ChYjYFsp590MECLVi3qCNb9MGJCh1IOjssZw4CDY+wahn0Pwcg8YSBny6pBrzG1x6iEAjgtcf6gjUKGkmwfoDdiCfJUmmCTgBYGuVbxaQIQlhUbU7d4DUK8GJUJD1yWALNi1MSnZBikI4UlUOU4DeLJohHqKSgk4AKEDcDqK99lwMsgibyNDa0kKGciAJBpPcNRjm/CYtbgNXB1GSwJh8YvijuBHBPQ4VpCAXKuUNQJ5B5w5CDa5l4UBQhEAYgQSEGrFx1CgXu9RoRSh1nPO3Zy+wuGPSYlMQFQRCQXVcDb22ZhAwbIevwYYMr38OD7PZEIjkYCxHL+z0e0H8zaA2EqoLAoB1wvwxD7l78uESs2ahjTmdoAADQVA4taQ3BuwcVKgFk1EkIiE1GRo6bvWtpu7CwAJoyaAC40FAgFs0eXZZWGOsNhYCAclYBJEsQNFZOGEWJoJgUIJysls6yikWG2RFg4awNdIXgoh1vOQGHxGc6/QiKM6QXwHfP8AcHlS2gFpbQWZI51ETIrJ1BLJVAMMAugcX6cEeMOVDAAiME2/F4S4LICQExlhjuL4wxhROEUIYExgBbqAZYseE0aoZgc34EIR4d6uALRBTBrb7YyWYBg9IdVsAmI0NIEBP2gDjq2k4bDvCTlkUkKROriBtvAMKzvZL+eeYdh4ccEvXEARoGME1bwu40ZhN3s6IDOwkkhD0Q6RMOBAUWFZAmg6AMyoLJqREEInu3HSbhuEQSceAatdtzMZ1dIhCoYBEk1VwgSVQ0hEuFKbRqLem/npMQYeOHO18KU0lkb8evQhucyo8YPIdulZuBEpDA1AUOKw4JlLS4YX7l4BDpBNKRFjI5YsaQt4WUV45UhT1b0h20UwhAUsLDAFRxOsIOwCOv6+5wNgAPWGHjYp524JIrsX/QucwYRb2Rt4AyyVghj98bjzZzNhVrJgdOkBgBHps+HrHIk5L7mA5C/nmsIEi5YtcvKgGfnOHN7Rqv8ATw5wb0xaLczayBrQXeBgCIJCOQRntBGCABs7BByDzhK8ZbrgUdZwSs6QyYD86F0MmmhKTsBlsV86HSqnW4wQWHiHqBMZ0PDDjmoaCXMBt0lL0EkbvIWad/gLqXDYjkciA3QDAAHQNkamGgDHQK3CwrfPaFpCPiVp+IfSZAwW5Ank+vCIggXYRlOOPFFdIxYoElo4eOMBlfM1GszEQQ85w1FE6wIG7AnODQVHEr0geZbQQTOpSo6RqCJ3Ny0Bh8GuMbLlubywZyoNtc2C9HbWB+qZjkKF8cSiDupH+z4lOEAwO0FhCOIWrcAdoQAEW/x0+FBg2h2iiRchDrQF8w2JYzhiSzT+UFjIAQBCm16SmAHALh/xvIRXAjEZwaO0T+kYDHpdjmx2/HaWUj9OQpzCeoH2Hg4v+4MEASbRl4Of4/ajCEYIUQvnP2G3Ej5H1Cixm+JF8xR/G5OgL+qr+c/ZZncjhuIUK/oG5r+j8Z0OP0APo9T9kIl0Ox0iJqCDY+hzqecjR/G4pl9ID6L7f4/aWXYw4b9IUL6Xfc3eOH43AMvoKI43xfaMvOB4Hb9fRipthVz/AI9vxmZ2j8fQojjn3fbwZnyuUyR+Aev0CQzSP79IAARg/i+pj5+sC5Pz9ykfmG36jYoMTJ/x6f8AA/BbNwP1gX3e6ZXA6iExOlk8D4X/AAB+DxNIKgVKX7topa8RNMDmnwg19sT0EZ8tf1u56QjPKEnQ9NaH4h66LFkuXHmaX2kDXb+mOCC3GxQW2mPumHS7qx6nF4Hd/JCIWYMsKMHuevqfiWwkKPQuj0hgsnJgzmp1/XDrbp90R8NukQ6ZraaRMshDWaIpxCcAIIwfwwjuelwEVMJul2zL3fedNwDCYmUvGtfI/DGeYK4EhY8D97wHP6LQBTwI7j8NQbj7Q7mU53Y+8F0UMs5UsZF9oEMcgfhE0cMrXwMz++xITSOYzBsPqM1zGR1+8QYZFjpDzRQVsHYah7uEOmwDLAHPFDJDRYBni6J4ZiHoLADirA/T8IiPCi0yNBhYaFQfoQ+Q9qN7wAm+UCuFkkukcDXlCIOHWFkAqABswwqP3wNhtWyOgskcSMx47iMJfIA9kxv5Am9zoZahqtAfU2PGWtPwh5QaYsPY5EJ083WD4IJGoFI/dzC2ilAPl99SMgwXuvixORZOXSsvEfGvai7H6f/EACkQAQACAgICAQMFAQEBAQAAAAERIQAxQVFhcYGRobEwQMHR8BDx4SD/2gAIAQEAAT8Q/YXWQzosfsY0PDeS9Tsd6HwmHy8B5cUsoniCpVr5ORI6NCzZAmo4wKJoDAsQAkva4cbqACTExS9pgyPPFSYGjuga5Bw5GQFkFQCXaZNzP701DZ6ApfRhur1aCW4AG48roJFlgjwiA+DN2FojmI4d3tY3uW4Rsew9Jx5ypgM3Mi8aOvKpCpEk2mo8i+rNYBHUxaxxLLidt64+/LKTybOjknCwCcAuaGuHuZiKUl373pCsjEhtL2EdC3U4S0LI8yol5dF0BHOMoykh3zmJ4wYMAFjsViBh/HnOH3pz0w8xYla+SJOMTDFCEnYbfeG6SWng6+eXRiLn0SH76eTS9toU3bklhNeQ6fmH70H/AJR7nuUE8XD7sJfQfghghBr0hSUtGRpLxYqJfjFq+yDPrnBg8Js+jjUEe4DEXIhIeco+BBn6tSYSVsjDym4aE/IpoGcLj91Jf9s1/eaWpVjpcsRoS03ALINd+uI67Jw0WNrKasfGW0E4hzAqgkOYjB1oaYGv6WF941ny/OI1/wA2/sUZHeNRm8epynUmkDTAwummcYp4a1mIJyBgRrU8NTBl60APyfujWtF5zTxLApJd0Ci6NrFMtaamzviKSky0QWIBMuXnDticPKQPUpSqGc7uQExOh65ciyi19XClLROKIBQanHGZMoGS90a5xiAiEATzyR0oyyVtqEq0XL5nnBIpsUdaRy17d/iOvBOYXwDvh0JQcJ+4i+6uvgff4LHe4NFGpZtJrAqurMIwW9kvBWzZrpwqZxSRb0YlYNvkBDm4l4/LBQ894GrKLROPBzp7QNW3SYhINd+sSVXb5r4yCqudu6wBRGqr4nw95y9qjf8AZ0ThIKSIXVoyIvxzGLqQBqsQ9X9vlz5nJt9IcDYASI6q+7rFSLLfa8eZ7nCUaeZ686MjTQi2k1L+8QOMOowagJB8Sv8AmYZFocPGH1unvHobeUy4Dbl9f0M/fxHOJU/VbJy+tBjQR6eOEeMhZ1Hna8e8lJUSCfLHS8cYT2ER0nwBH9ul1Ws6h9eMpyI6PwVfA0+IZROllerePRgoPEnXIyYcGOd8/wCY3krRIIQ0UpA8HFFlBx7t0ggaErcEMAxJRoHLvYYRdETPirYgHlA9nnIJ6HIb8cf1rI0dCpwRW3fGPYKiz59GJJDTybCd+tGVwJKxL3x2ZC4G0vnpMGylRhSL2P7d4waN9XBOLsggTqdW/gwCVOjZw99uQSsvRzyz3hMmd7qmP6waICde+nmciaJJUSfFveJYRcKly+r0ZF2NR98MSc5d9cTCf/mMNng8384xe/bMz0VWCBCGSVa2iOTBloRbSYMKI6JK2HFxqa3iTC2ESliUrxhGaZqNk/tkL509YmRtQ6scuATeDAkRU6RZO26X67wpFIinFrrqccHV+X/Op5xH8G//ALklFat7/gTDMuH0YNxwdUs/+TGJlfzpNSeTeElLbmIXfonEQlr5mezeNp0bOG2MpM67JiOeHFnvIqEyRsAmTHjUC9PjwTkOowkj6qGsqBaH7HZLMSQ0QCBUpXa/tjewyzUZOVVSyVcPQZFnIbJ8jiGcshSdlGyXTnfOKgj7LdDvWdDiouDWKAK/A4LPZsI9g8VzzhGFs6VEUbR/DeNBOxtiybyYhQ1+bygdLo38Y7axO9IjlaGMbQLJNvA9nbiDQR9B/NY6H1CCsk3J6MhnXLUDylqHjNi8o/G9CHtTGuVEkILI4U/bIxSS+9gVEDqlcFHNM7BWJ2i2OMYQAIG13rZKc3FOJChh760+kwGjT18SvkrIsS3o9dOErzGmgfn6YVIwcdONxmzTgJnZDqo9Y/jKnnG8UDr3/HWEJUahMzynP14yArEc849GTpNLykN3HZgukBWyu4kYPEY9g3Js3BzHzkugQJ9H7ZR7oGi2nT0WScNBpbETgrlOBO388DF2EXAcJ1+XknrLEWeh2GB2SXbHHT/kxESoZL15XSbMVbMIhUVhaA9P/vZhE/y4BThnuJ94w0nxxH8fxkOvubyPBKG5LXoBPnISgPAAHwRhVT1hE37fS6xffElmw4QETKn2VOUFsG7OlfFed4DKPbLCH0pAdaxVfVz9Y0H+nGFhLUBOvHWIS2Px6MEn1FxCASuOYyTPM8jikGji9tTPI8nOM8BNADk0AHbVbxzpf/d1OLLwYYGL/b1+3w1oh9MnXHDr6ObVkqDQQSg3C8oFcIjdjE9dv8d4zJ0O6wh84RRV0mvnEFDfuchIL6YLGPkcd+8EeKrx0xtyIYJu1o+/0sB5P/NhyDBBftlEK0Fy/cF+uRLhBonlN/QhG9YXOua2biL+X44cRRn/ABUv4OuskSa6nXrFsk394bEyemDifpewl5uMeUU7qezCADJi3xvCHyjQyGRKEE6ZwnvKfOfZzhjJlGeEGj0ftkA0zkhY++BLrGc7EmGCYGDRT+8MhZh5eLqcRbR1x5T3GFb2qE3B4yQcayxZCY3oMUcNNwb9uElMnDGbCzwX7jIFPLAAHbVX25HBZtmCnw1wD0fvYdckI/5JH+OJ+1NwGfoPOE8khcPcarjrLhleXHft5fLv5OfBihencMmpR2ujNzlIWcWvU/X65PdLL2iOfn7rggEUfJ5V8axsyrybHhnKwlOt7HA/Z3kwB5vz49ueTJGen+jHNuAcz4uMRBuz5/xpxWMifmInxngLfwhP9sBlE6y96T+T837QyT0NvoxJ7eOBwGWJwTYkDJyDZGWW2fPXQGV1emT6eeFgwlXVJzBoPgxhyEMiLDtO3rqcEymno4L328zjbgIjgQJY0fIRD38k/PGSTaODsb8/xgNFU13Eh5DoNZJBwBebyQeHPqfuHY9OOvHyc/5cbg/+IxjH356cAhjbB9SDgARkf2SS1+RYpMvBwHRhWcs73BYjrzHZw8OL5fPy6r3nmNKzYx9kZMgspLhOa7XGw4iijD1xA4yKMEhfbHca+MAMkPEH+5ch0SJXtX/uQE63tMVx4wBbFWwOIVsJ+2JYKkB2PZ/Jkrit73HMPi/jLhAZ7lB+W4ACMjkxK6mhV9QwlwzdrO/Wb2JfsXQgccrgPLjdwccBiRLgoDjCc+g8nz2+O8QSEjcVXEefxk86gB1I+o+9E5YqvfrxlLYbd1LRL44jvIGaAUcKuYuDxtwiCgUjY/8AjOasSU0mTNT8YJ3Sr9dnrAJIHd1yHnrHN0RTqTyd/wAYpPdkcEnOSaDdO1LThfzbGoPCOCvmk7bbygXJQkj2/iCPXPmI/wCAfYf0P2E0jDQ29YeLAaNP99vOUEusnQlKAFqdAYZCRVUT1JB3doBToSEnKREhGBhjU2F2SnAecFR2FyrR+A8YEQpcnzwHXeUIOoapRWqR7PE5AC0tdEW+uJblR0FdOq+uMiCy1MJzTkePVPH3MTQ32slXP+MQIQIPgiT2f5ww41g15Sz9MiRt2Oi0ldcY+EfM/fWb1cPUedOcIQkAhE6TFSlwXbfe5k1ieX8IB+uZt6Fp0GLm1Q6OCXhWnANDSmvI4xZMZOn3Pe/1MTXhVJkkcqSHcWwq8XcFoQoEpIJBlxklsSkzC/BB92siWETzzk9lqsrojDE403XyTy3vFGg3PfgdBgHoS+psPoduM0FgSAcJODcm0do2ncOeXGgJQa8TOBQxLUCd1vsH4jElgDKaHF+8UZUBQ8cH04SLQOkWPXGTeie45vMYTnBEExPDPWnoBL/zHUZP7r9ZJwmOTi8+DgesHmrNtHnuvvjyVFD/ACcHKjjDZbQ9lWA7gMjjSZk7A12WK7ygmiuPhCcLdtiTdp8zDzTmSdIBSAMCDg4jcB/Lv3k1JJJXeSykVL2J+gHRvBcCoHlnfYesUleaPB40+sdUdrd3+DEQSwHgcfRMMQ4M2jrs4XrjJLrx7xvCccKTPv644C43tM8hZsyxEanwNkHceHWWEsM2lvX+RiCmhXTktw32WHyxXKCJZG+w8xDGP+A5/wD7nhxuv8mPyfqCVIDa0GH3LhanyHB5cfMVaaDOhvOjN9sR75e970PjCzCgPy9rtduXV4O/Y4AaK2U8ZS/wKJKKVSKIORzmAKE3DljCLXEQHDGNt6AbXiXIYe7InIpTByt7/vkyScLtGjNA8h3ggWBWR4OD6fXBR1RFqhx4I4++TElGe6YM0dgOwcn/AN4YF4bKQiJtbPneNaWo12NHzhSGkatT44k2xWIvdi8dHZW2yM4qsEiphlVuI73gFaTiKEwJ8bMdYfgzXmMzVZHbz6/5JmCTzcT8sfGO6Cg9LkyudI6hP6QskAWujF+Je94+v8rW4M1htcO0bvQ/OQ1WgNf0PwYZWfkC18uEHIYuuha5KT34PTHDIWep2pcMcdkNEhSYMdkKx3gDxJTpp0UAlQSokDJTEoVQTJHPeBJ5MI1r+vvkTgng3RJMDpULMfL/AAPo4hptK34RhzTLOiQPPp3BF5JiBUgaY0r6wDHkAup7aqq5xDQIPb8fzl2SekwPt3jtCAdGhCzi8cGkUkeOnJDGpjwB0KAesoOhQZieS5nrebhzVcs67Z52AwemXyGHdEUF5pEZMqX9iDCBofovoYSfg5tD60Ij9JS5jHzqB5OMOHe5JFGxLHR9mEkJ0Ie1w1cAt+YFe6MASe8fzn2e2E7NB3eIEUHOQsd+RdBk+ApaYNe8VI6ht5Sywtr65aCVWSFCPREhyPpl+3EQLH1RQyT5NKDOSIA6gDiIJiHCXWqxysydzMvrRipbRoXGo/OVCBoAaO+yO/EY0JRqV8sL84mtFJt9ujBlFCUNq4jEknTyI7wUjpZTjxshzDb45I08RkKiYEotov4Y13GkSqQGaoOkzXKCpOCuQLyIRErjgW4z+YNyNi3O4H4gMUsrxW6ndS9+v4fpBTp5bDGuJ1iSCiQCEgoIiDB7lzg4oJJW2IFTnmnnRCK00mA2tr60HaqHKhhqbxTzBthEW2u41tw0A2EuuGaFSrYJdgBLFRSMTYiPWy9cVSBWlmeoiCZsSeiMKehXFDoCR5qRfEa45xACREt9v85xl7J14Or+2TGhl+Awy9EIag485GpCvUNG7Pa5BG3PVGzSdmRSovcEuxnEaPKrsOo628YGQBh7eaO3EAaRFEZoeHcecFShS9Np85ZsgQ8o7eSLenEfxvuzwnZluCB9nHtA4EaPNz9cifvdir4oyuFUb4/r5YsM5ROtIMB7KklkrmN7CHjAaJJDUtT4Kfo+Yc01ujiNRimIL0ZGz4bHGMqSQxWS2BSB08CcO05mFF5I8jbq3F2CwdWsEdIhTymZMIcd3zDISGaEGfHVcnpQxbvssEWncjRAaMAB0cmsLZRU/cs2rDJqfGeqIjVEhJUoFLpQLTgejQx1xQXgj8/GRpYU/Hb1/XeI5dFU+iHEYYh0bfsrIiq7ty0Ywj64mT14wM+CuLGhMh7tQXtP4+59YBktllTOMdh5uPfhhychT9uVOp3lWqZsV3/51gBNk8R9awCchJam9PxXGCX2Dl/8Ybqh5v8AtbGtDCErIs0/KaAy08t5A92jaBb83gjQYPj+X9IA1/xh/ZHrQknqCDq+d0lRrSmR0RlDMLjEyKuSxofJkFU4OENxM2056cYUHmITca5V9jYlYl8DCllTpyAIRxgSQp1eKCEBWo38MWXZSDDPgelEtCQSq5xwvCZgoXXmeXC4WEHjt+cnodbCR8jse84t/hfzlQAvc8afAcRkgEvoI+IxRSOBbJq8hHLFGIBa0jMDR7i/JeOIS3rhwNEgJTXwbQyEzQS8kaE7Ls04u9s8Bs+n2wFzkuhmR6dScfTJYm8HljLxz+snnRJgm3y4DtiPyqVMr0Bwp2Na0cdAfUSTbGGjZ8unZBf6h9aseuLTIm1sMCnI162hMTOgueCwD4xAakfayMTh5FuB+sBycdCcQyeWRGbZKIoXOG/SezcwFCZyIUDAGcbSg+2E4W+6fSheKiQk06cN2Ii6gE6mToHGBMZs9C3PN9u46jJLpFUNwH0r65azN8qSN+v7w5w1oNtR32/bIcBzCvA9uSiEM8UXoMokkNSJhNE5uvGEVH2TyFSkSx4wyyeepnDLWVEZmDbP5ZU4Fvab1La5NAbQSPv8mFEpJEhBYvXfjEDhm3Dwc+cFSPlSq6YpB05O3M4uFivgOVenqNgvkaPaZzo8ccEYAAAEAEAGg/TLi2YCSlaALXNwXzkUfuTG8yIJKXMEMhODjohFkOAByYJEzaJWmJItFnxwafwNrK6gg7y6bMp3E2jBk4R2rZEEqrQTCzTCyS5o9haPK1IBTJTWKIRCWpgaijHfkjkWRwNDOzH5EmrBdk8zg2ASxGEUBuTAAtkJnjm3biSaACV2eJ2w+XWQbQpffkffBgULcRiRwOumO5p8mUklJCC2y3gDX2MBobaT+f8ARhOEojSWDt3HvrD6QswUtCoVIl947BI34V5V7bwrMwfUz0FO/HnOxMB42K/Rpx9ESvgdt2cYbSsoiQ5356ynKTvd33TXisI/UMqKpxwg5bZJvDmXv+36kj7CuVUYmpDZxao6Kqw8tk2bgqTQKBKat+DnBWopeRSjOSnCxJ0StOKEMRFSoYhKCw1EQhaBMgpyF4JtsepSmd2iRsGpoiVTBGoEURAZsTqPJX9GRszBoOJqA1q1cgxY3qWe1dTpNwZM3WPCaDhg73hBIJJKUdidScZAD5JLPf8AaTBGh8ulwxQ2SJZssL5TXesg5Q5dPXX4clCK3f39fzlgKUl0+j5KyUSUBlKHnsQ3xkQAYMwwOD34MulR1rYRHQOMQBhaonf8GG2YCHmMEfTBXrHIFYArIArFsFxDOckcaCuvHEoxx+nRV3pIVZ60viWTOLNXNsCC2m6bZDK5FzTHXMGcWwMIAGonE3IVmCgUZwuJBEUiiCGuwtghWThW1wyuVSuWmIXmoAjgkxzEA1JzHN5tc9RsCJ6qxmaZehgoJmEKoEsG+JbiKJmsENO5IzimmSHaI/i+YyyLWTQjUPgM0OFS2Og6aPkM+2advKw7B4j6OCQM8EdsG+2YrAoWPdP9dYyKW6QJePGDooBO29M5CpsZjhwlDCLZA1KbcTJA4fWh4ZkR1imQVrsjjfAdbMHxFUszdh8ajCOn3jW9ZQLWnImMaEJGzr08jsckAPkbjuP9Oc9ufLouhV+kAVYDnEjkL3xkc6SGxHul3Tx8NdRJxHhKYBVCTCSOGgyqQHI4tAOkTPORbUScDA4p5m742AgFyiSeJZnMS/nrqkhkUOet/hERzYjgDtBKLTWCCyYKQ5GpQkVIEEBA0A3OEs5LjlUBSwIpvOaS0BKRQRrhMQkEJ4I4g6QvAeXcs3ZET284UQg9WZZIdHXB1/7kwCsindNg81wlmMSpjzMvRuzFFuWD8xH4esBsr/fnwdBbtcZJ0/UlMhSL2yWv1jGnfQ9vvURWNLKHZE/J3ODmNIepkZx2DUFaCMnvc1fpfuwA0npfiJ5cQo3rxJ/R27ZxPVd0Eb1A/Sxik+vTzNeCKghcrGsS6QyRChYgYJkwFmWYNjeTJj0BzkZEgOYnKROTw6WCnYxT04mtYY2aJBxLNkevDnGGAJawFCkpl4Q4lvTGZaTPIaoVgd2kzUwMg55F47ETwlYgxQ1ngek8yLcDTSkN7KOQYQB7ZYh4SGEKTP1RigWCDYsJirhl56/icgKqRykHjIvgJEqTZ666cGNzEzv5zYTsh64cmXbxgbyVhqyMceA1RX897nBaFrx3PfZ2YBJIsKIEiTtJkAc2BDZS09Pec7U3FxuDk84FGg3IV4jeASDxRSY2A+DKEx2oOHvr4zdaaXv1jwSFDYkibK9g+dYRd6NI8PpEh2Un6EHEknjZDwG+D3AxRnKsYOvLzSGHyP4mYx8CImuyROM8xGzQskqQNom2BF75BlzVEk05IuDxZKzqYyTiI0iok+5BrGIjUYpCIARGQWFSvLhODKkUWgMlaSCTyR9+LO3AzeYOQkgjhaJsMMVFkwYIbO6u7orMsJgK8CrIIeRAqjeKSmxl273ucCgoWujuDJCdq6X43DyySQEm/PxrDWAkSpoNr4yANKdG08YCcBAEmweTJLiyGw+7fgytUuBwjPkdJrK+IImZle/UYgJqni8cehziAWghjnpPGDnB6vxEMN95N2B5SyiSvAxYkINhs9xkiUIagle/jvEsyyvIQDwcjycJkYIYWHvQ/Pl1xi8ZVCd7whRBnudG7rUA/QVc53id4YZIo1IItlPU8eOsm1qrEHMhaDADELoggIiQDcK8AVqxqNowFmcAbBlK2pqio6uqCMJOFYCJ1TWo3pQxXpaPKRsWZddviF4EioIUZ0BXa7CqBxo7giBINgMU6t66pVmbbAJZMFSzLAJIQ6MWJWTDp3JyAW5A7IZWzIHAzYR0ct/RiSy9XsfOStQ7h58yl7IK4/GDeHPYXPZH5nE7Rz7/AIec3IKEkbg5k58YR3ckds8Jk59Qcdn5yWgw8jkyeqy6EIMI5AsD7f24xXWplUmYjj13hw6XTv8A9cQBhi0NeusCsrve355vXWQGBjSZTaC0Oe8XtFJAl7kHOIl6MJQneS1DgG7KcjRBEESxH9B/KD1JpCAcBHBIkTOJOvQfEkGBifXdYik+GQgYLi1zRqZxptWbZzJXEQUxLAE3TrjYLRMWMM8d7dzxGOSWBLNTcmbqUVFJEiLcwhukQvqIhVCGMNo8SWI0FrkJzThjZM3azuyhFrtpL7OdIpSlRJQxi41a5PKYsO8h7ba55QYCKInAUKFAXjhKgRRU0wLDUwhCZJpEYII3gEhTZO3NDbjgwJgpKmEXZvCYkoASmrkrrFpJXFlwul11kqTYExFgiCyIyKVGNJGJQwnwKeIZ4xmHcXbdhMMQwCFYA9Yzi56tI8YQvTZvt6QrYNdnJfhOVxqvQsZOGR64L+X+i8dMq4sQhP500uMW5dP4/hnwzxPEQPEUCuR1jL6XXfelomepKrTMJwtwuiUkwqcbLcDplzcNoQAEaOAOFQUPAbEbqUlQufQZtCSm2nCS5wg+iWoMTp0PDmOoqB+9VXQw525tP+T0DNAIxNwZSB8JAWMKZm4OLFJkC2DWTT1wWGZvyrLSaARhBuWSQkf82yuRTH2nGOTbGziCW/hN5v76I3RRmQQvlcUdvksRPpoSyGcTNqikIhU3MOW6BeKKzVcGcHKBMmPZVkCAQm4qOuGSdyZagZddYBByNa01gm6LIUE5mOSyhggZErhmQUKAQrDLW8oT8QMssnIjZIfpm1qBPeQmTiVepDNwHz004Oy1CryIEduE6wKHLyuI31amJLOxDJQqzANykwzjIjIrgLEtB46AgxA10mqQy0J0krgw3Q0kiAELMbkAiKoF+rlaCQ0oNA8gQGlonDF0IjG6jBZTrReJKh9oZJHPAA1nGJ8eF9yQMRBI7Mx0L65x0op0xI8B58YifI5FaDYp9nR7dYZp2aApR+sbGAwKtWXwOyMnEQi6CbKAAejXGTY4fARRohlYtGjjt8mMw4nVcbpCuSMI+ZxOoMQ7EuMNKESj6U2vsSOQg1xfvWSs8/nHo8T7vHCtcyQIzBXIpIcvaTC20dbrowBkh2bU2toMK1pJsmEU/gTDuHROH9T/AMHRkHGZXgnHqByJpa+jwETyydS+189QT2wS1SqFiGhFADDUNjgOlSpo9RUCl0i4Bj7ohNiTjRlJcPOKOlUvdjhwhZjczBmkjeuxgPkaJEN+i6LgY8XTpeDoH2TWORQSeKj7gwJlKzXKdtMPMiayFzBVIAhv16y0wZROgpkCYbQxRZqCEYCbFUmVTIuwNX8aAnDZNo6xqc0xOlz7hYyJRiF991hbQqyxCQ2HiowyOAi9voEBgYjYdmCHdaOsrWf7/oyhKxTERUu8gnr73rxOPEVTgjWSYQTKneY7O9ZfTkSRGkQvsImPf7AYcuF/iFJi3h76+ArAuTMUKO8ihRznh0M27dpT22s6rJZxIGHmL8DVxJ74bIJtXRBALJRtpubCEjQlIwaCMWrbZbXCUByY/LYZygMCrELO7xBKs/08jiQpOxiEjtgIDEALLvWM93rAwVoUwhakmCZjmCgRnCFLQvwSUjrBrimG5RJzBQASE0BLmci0og2HfQ4gh+5mdLCfniM3P2RC9Gs3oPL/ADTjQXOkoJp1EneHCABoIMMfbKJSJcKEACIyI2I/txpVHYMTYhfmqYWVeiw/EYSqOABkJQ47X2y4MRbySFUIqJtkFFswqwAxQYwpTokMsANBoA//ABLNSH7CwJQgUVsSkxzHnnKUx5mQJbf/AAP2/wDgm8FgCDIExNyVfWfoQhgo9To/z84STihwEHHmo8Qig/EH9vd3jkxjgMqz5g/sP0Kgl/Ox+QzT0/8AHHKByYqnF7n8+ftpP8hyHAQGHeD54+36KGUO9CwkMtGAwXVP4xfh/bF3llA/8QYZDj+N/RoyUnrdsjtLHQmzCF/5Kdov8vhnD9qIhr/bJd5PWSMi6Z9h+kmmYEbGvflkjkd4dl0jjS8z+2h9YP7aoNczc/8AMu/yB+kJEcjDZr/Wf5whxinkuVI+7+v7bIXWE1x2wHdv7j9MnJPZSOx8llJ7b4RsPAz6hm5v3YUPlJjpyAj2Nn7Wr332Gb4YMSR3+6f1E0SYAve+xvFZxCGSSli9F/GR/wDhSfsQG3/FOHK5D/8AST+rS8MvTZ/If8QiFEfUNv5V+Z/+Iv2Jo8H6mIsdD2Ye3PwfqxdimZnHcYY2ZOA/j/oGJA7ciOwmfZ87xfj7Z4MkfCLf2iNhxAkcAwMRVYex6wkxOj/UZcflOeCozrQfqlqkexM8IfgYyqjoJ0P0AYkLMFdHo0ZK6FEq5ykMgD6T+0NkoAEEQeXZi65di3ePB9uQ6UfnfX3/AFVQyEV02Map2ezTnLFlPS6QMV2AeTHLkBE0jZ+zoPJJ6aPvk3yN+8kIw/h5r+4v9ZBmvrFP4/4Ahe8Ukt/hX7M4il8rRnI94csrwmXPEc/x8/rak1CfpjRwKYyhuC8TsfsxX434tgS76CscSwuv98frT9T8xyCHjErCSWEBHDCzJWw6Sy/2Td8212QocBfzn1QhjhX6+hE4owxzpPid4gTh+qD8xByvT2ZOymqY2DK6VgjiHaBMdZUzMhHiL7kSd2L7cZoxU2sqduca/ZDi23Jw5DSfTBxWgSQNQApQpKMeyLBHBnlFWyzeAK3GzecVAy5bMCP1pnVY9ypWV8tnQJ9KVQtE6fYgDCOs7dFgC7sprrHSw/QyDeVo0AQoj+yGwkKbn6544TJl490+3Oj9ib7ZaxsupPLIUpL9fy2bO2EER5QnWslXu+0rJWGWW7+yT53/AA//xAAmEQABAwIGAgIDAAAAAAAAAAABADFAMEECEBEgIXFQYDJRYYHw/9oACAECAQk/AKRTPCfJk0F88Wg+oN+U65xwHyGuKy+V/CncNShp5N8n3dU3Tp4Lp6XdB1yYjmpfdckofkp/Um9FBQqlYjvdol4ncW0RjxU4oDUxe54P9+k1DCE8X//EACgRAAECBQQABgMAAAAAAAAAAAEAMQIRITBxECBAQSIyUFGBkWBh4v/aAAgBAwEJPwCy6hMvgD7/AJWOExdGnDbWGZ4ONKQNfZNoZQI+H0R0NxkEZ9XM2PfiZt+XRt2bflTJu97Na8qpCE1l2Wd9AqBNaYrum740YXHFdxoBDD9o+0IuYTBNwjJVm59Sf8FiUUzeG9jXhZGnXDdimNFjhN2mTG3ix8JxW5M4B3ujIfpNxMc4yUQmh4t/dVGZpuL/AP/Z");
		return productImage;
	}
	
	public static ProductReview createProductReview() {

		ProductReview productReview = new ProductReview();
		productReview.setNote(quantity);
		productReview.setDescription("Test Name" + Calendar.getInstance().getTimeInMillis());
		productReview.setReviewDate(date);

		return productReview;
	}
}

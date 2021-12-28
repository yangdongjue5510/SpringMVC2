package hello.itemservice.web.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.form.ItemSaveForm;
import hello.itemservice.domain.form.ItemUpdateForm;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

	private final ItemRepository itemRepository;

	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "validation/v4/items";
	}

	@GetMapping("/{itemId}")
	public String item(@PathVariable long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v4/item";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "validation/v4/addForm";
	}

	@PostMapping("/add") //item을 검증후 bindingResult에 저장
	public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult,
		RedirectAttributes redirectAttributes, Model model) {
		//특정 필드가 아닌 복합 검증
		if (form.getPrice() != null && form.getQuantity() != null) {
			int result = form.getPrice() * form.getQuantity();
			if (result < 10000) {
				//bindingResult.addError(new ObjectError("form", new String[]{"totalPriceMin"}, new Object[]{10000, result}, null));
				bindingResult.reject("totalPriceMin", new Object[] {10000, result}, null);
			}
		}

		if (bindingResult.hasErrors()) {
			//bindingResult는 자연스럽게 뷰로 전달되서 모델로 넣을 필요 없다.
			return "validation/v4/addForm";
		}

		//성공로직
		Item item = new Item();
		item.setItemName(form.getItemName());
		item.setPrice(form.getPrice());
		item.setQuantity(form.getQuantity());
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v4/items/{itemId}";
	}

	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v4/editForm";
	}

	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {
		//특정 필드가 아닌 복합 검증
		if (form.getPrice() != null && form.getQuantity() != null) {
			int result = form.getPrice() * form.getQuantity();
			if (result < 10000) {
				//bindingResult.addError(new ObjectError("form", new String[]{"totalPriceMin"}, new Object[]{10000, result}, null));
				bindingResult.reject("totalPriceMin", new Object[] {10000, result}, null);
			}
		}

		if (bindingResult.hasErrors()) {
			return "validation/v4/editForm";
		}

		Item item = new Item();
		item.setItemName(form.getItemName());
		item.setPrice(form.getPrice());
		item.setQuantity(form.getQuantity());
		itemRepository.update(itemId, item);
		return "redirect:/validation/v4/items/{itemId}";
	}
}
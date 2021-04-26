# simple-form

##### Add this in your project's build.gradle (project level)
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

##### Add this in your project's build.gradle (app level)
```gradle
	dependencies {
	        implementation 'com.github.lspradeep:simple-form:v1.0.0'
	}
```
## Table of content
### 1. How to create a simple form
### 2. How to create a sectioned form
### 3. How to create a sectioned form and show only one section at a time
<br/><br/><br/>


# 1. How to create a simple form

|               |               | 
| ------------- | ------------- |
| ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/simple1.png)  | ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/simple2.png)|

### Step 1:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.pradeep.form.simple_form.presentation.SimpleFormView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### Step 2: (just describe your form item)
```kotlin
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form

fun getFormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Bio",
                description = "tell us about you",
                hint = "I'm from India. I love ice cream.",
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Gender",
                hint = "please choose your gender",
                choices = listOf(
                    "Male",
                    "Female",
                    "Others"
                ),
            )
        )
        return forms
    }
```

### Step 3: 
```kotlin
binding.simpleForm.setData(getFormData(), callback = this)
```

### Step 4: 
```kotlin
implement FormSubmitCallback in your activity and override the following function 

    override fun onFormSubmitted(forms: List<Form>) {
         //do something with the result
    }
```

<br/><br/>

# 2. How to create a sectioned form

|               |               |               | 
| ------------- | ------------- | ------------- |
| ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/sectioned1.png)  | ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/sectioned2.png)|![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/sectioned3.png)|


### Step 1:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.pradeep.form.simple_form.presentation.SimpleFormView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### Step 2: (just describe your form item)
```kotlin
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form

fun getSection1FormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
            )
        )
        return forms
    }

    fun getSection2FormData(): List<Form> {
        val forms = mutableListOf<Form>()
     forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Bio",
                description = "tell us about you",
                hint = "I'm from India. I love ice cream.",
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Gender",
                hint = "please choose your gender",
                choices = listOf(
                    "Male",
                    "Female",
                    "Others"
                ),
            )
        )
        return forms
    }
```

### Step 3: 
```kotlin
   private var sectionedForms = mutableMapOf<String, List<Form>>()
   sectionedForms["Personal Information"] = getSection1FormData()
   sectionedForms["Education"] = getSection2FormData()

   binding.simpleForm.setData(sectionedForms, callback = this)
```

### Step 4: 
```kotlin
implement FormSubmitCallback in your activity and override the following function 

    override fun onFormSubmitted(forms: List<Form>) {
         //do something with the result
    }
```

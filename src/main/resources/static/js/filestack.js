// window.addEventListener('DOMContentLoaded', function () {
//     const apikey = 'YOUR_APIKEY';
//     const client = filestack.init(apikey);
//
//     document.querySelector('input').addEventListener('change', (event) => {
//         const files = event.target.files;
//         const pickOptions = {
//             transformations: {
//                 crop: {
//                     aspectRatio: 16/9,
//                 },
//                 circle: false
//             },
//             onUploadDone: res => console.log(res),
//         };
//         const picker = client.picker(pickOptions);
//         picker.crop(files);
//     });
// });

// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
// NOT SURE OF ANYTHING ABOVE THIS LINE :) -BRYAN


// Set up the picker
const client = filestack.init(filestackKey);

//set up picker options
const options = {
    onUploadDone: updateForm,
    maxSize: 10 * 500 * 500,
    accept: 'image/*',
    uploadInBackground: false,
};
const picker = client.picker(options);

// Get references to the DOM elements

const form = document.getElementById('pick-form');
const fileInput = document.getElementById('fileupload');
const btn = document.getElementById('picker');
const nameBox = document.getElementById('nameBox');
const urlBox = document.getElementById('urlBox');

// Add our event listeners
// Open modal
btn.addEventListener('click', function (e) {
    e.preventDefault();
    picker.open();
});

//submit
form.addEventListener('submit', function (e) {
    e.preventDefault();
    alert('Submitting: ' + fileInput.value);
});

// Helper to overwrite the field input value

function updateForm(result) {
    const fileData = result.filesUploaded[0];
    fileInput.value = fileData.url;

    // Some ugly DOM code to show some data.
    const name = document.createTextNode('Selected: ' + fileData.filename);
    const url = document.createElement('a');
    url.href = fileData.url;
    url.appendChild(document.createTextNode(fileData.url));
    nameBox.appendChild(name);
    urlBox.appendChild(document.createTextNode('Uploaded to: '));
    urlBox.appendChild(url);
};
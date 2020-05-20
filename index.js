const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

const db = admin.database();

exports.sendNotification = functions.database.ref('/Notifications/{userId}/{time}')
.onCreate(async (snapshot, context) => {
    let receiver = context.params.userId;
    let getToken = await db.ref('User/'+receiver+'/token').once('value');
    let token = getToken.val();
    let noti = snapshot.val();
    if(!token)
        return console.log('No register device');
    let listToken = [];
        listToken.push(token);
    let payload = {
        data: {
            customer: noti.customer,
            image: noti.image,
            product: noti.product,
            title: noti.title
        }
    };
    return admin.messaging().sendToDevice(listToken, payload);
});
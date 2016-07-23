from django.shortcuts import render
import KatalystApp.models
from django.http.response import HttpResponse
from django.shortcuts import render, redirect
# Create your views here.
from django.views.decorators.csrf import csrf_exempt
from KatalystApp.models import Mentor,Mentee,Appointment, AppointmentChat, Feedback
from gcm import GCM


def sendGCM(to, topic, message):
    gcm = GCM('AIzaSyD6-BUFptIOpKNrFv-Lz-6EHuHv07Hwg90')
    data = {'topic': topic, 'message': message}
    reg_id = to
    gcm.plaintext_request(registration_id=reg_id, data=data)

@csrf_exempt
def updateGcmMentor(request):
    if request.method == 'POST':
        m = Mentor.objects.get(mobile=request.POST['mobile'].strip())
        m.gcm = request.POST['gcm'].strip()
        m.save()
        return HttpResponse("Done")
    else:
        return HttpResponse("Post")


@csrf_exempt
def updateGcmMentee(request):
    if request.method == 'POST':
        m = Mentee.objects.get(mobile = request.POST['mobile'].strip())
        m.gcm = request.POST['gcm'].strip()
        m.save()
        return HttpResponse("Done")
    else:
        return HttpResponse("Post")


@csrf_exempt
def getMyChatsMentor(request):
    m = Mentor.objects.get(mobile=request.POST['mobile'].strip())
    s = ""
    for x in Appointment.objects.all():
        if(x.mentor.pk==m.pk):
            s = s + x.mentee.first_name + " " + x.mentee.last_name + " " + str(x.pk) + "~"
    return HttpResponse(s)

@csrf_exempt
def getMyChatsMentee(request):
    m = Mentee.objects.get(mobile=request.POST['mobile'].strip())
    s = ""
    for x in Appointment.objects.all():
        if(x.mentee.pk==m.pk):
            s = s + x.mentor.first_name + " " + x.mentor.last_name + " " + str(x.pk) + "~"
    return HttpResponse(s)

@csrf_exempt
def getSMS(request):
    mymobile = request.POST['mymobile'].strip()
    tomob = request.POST['tomobile'].strip() #message from
    text = request.POST['text'].strip()
    return HttpResponse("This is test response ")
    #Use this to respond accordingly

@csrf_exempt
def getChatHistoryMentor(request):
    aa = Appointment.objects.get(pk=request.POST['id'].strip())
    s = ""
    for xx in AppointmentChat.objects.all():
        if(xx.appointment.pk==aa.pk):
            s = s + xx.text + "," + str(xx.sender) +"~"
    return HttpResponse(s)

@csrf_exempt
def getChatHistoryMentee(request):
    aa = Appointment.objects.get(pk=request.POST['id'].strip())
    s = ""
    for xx in AppointmentChat.objects.all():
        if(xx.appointment.pk==aa.pk):
            s = s + xx.text + "," + str(xx.sender) +"~"
    return HttpResponse(s)

@csrf_exempt
def addQueryMessageMentor(request):
    aa = Appointment.objects.get(pk=request.POST['id'].strip())
    aap = AppointmentChat()
    aap.sender = 1
    aap.appointment=aa
    aap.text = request.POST['text'].strip()
    aap.save(force_insert=True)
    return HttpResponse("cool")

@csrf_exempt
def addQueryMessageMentee(request):
    aa = Appointment.objects.get(pk=request.POST['id'].strip())
    aap = AppointmentChat()
    aap.sender = 2
    aap.appointment=aa
    aap.text = request.POST['text'].strip()
    aap.save(force_insert=True)
    return HttpResponse("cool")


@csrf_exempt
def dashboard(request):
    context = {}
    context['mentors'] = Mentor.objects.all()
    context['mentees'] = Mentee.objects.all()
    context['appointments'] = Appointment.objects.all()
    context['feedbacks'] = Feedback.objects.all()
    return render(request, 'dashboard.html', context)

@csrf_exempt
def mentor(request):
    id = int(request.GET['id'].strip())
    mentor = Mentor.objects.get(id=id)
    context={}
    context['mentor'] = mentor
    apptlist = []
    for xx in Appointment.objects.all():
        if xx.mentor.pk==mentor.pk:
            apptlist.append(xx)
    context['apptlist'] = apptlist
    return render(request, 'mentor.html', context)

@csrf_exempt
def mentee(request):
    id = int(request.GET['id'].strip())
    mentee = Mentee.objects.get(id=id)
    context={}
    context['mentee'] = mentee
    apptlist = []
    for xx in Appointment.objects.all():
        if xx.mentee.pk==mentee.pk:
            apptlist.append(xx)
    context['apptlist'] = apptlist
    liscnt = []
    cnt = 0
    for yy in Appointment.objects.all():
        if(yy.mentee.pk==mentee.pk):
            cnt+=1
    context['cntval']=cnt
    return render(request, 'mentee.html', context)

@csrf_exempt
def getGoals(request):
    id = int(request.POST['id'].strip())
    s = ""
    for xx in (Appointment.objects.get(id=id)).goals.all():
        s = s+xx.name+"~"
    return HttpResponse(s)

@csrf_exempt
def allMentors(request):
    context={}
    context['mentors']=Mentor.objects.all()
    return render(request,'mentor-mentee-meetings.html',context)

@csrf_exempt
def allMentees(request):
    context={}
    context['mentees']=Mentee.objects.all()
    liscnt = []
    for xx in Mentee.objects.all():
        cnt = 0
        for yy in Appointment.objects.all():
            if(yy.mentee.pk==xx.pk):
                cnt+=1
        liscnt.append(cnt)
    context['cntval']=liscnt
    final = zip(Mentee.objects.all(),liscnt)
    context['final']=final
    return render(request,'mentor-mentee-meetings1.html',context)


@csrf_exempt
def meeting(request):
    id = int(request.GET['id'].strip())
    app = Appointment.objects.get(id=id)
    context={}
    context['app']=app

    return render(request,'mentor_address_format.html',context)


@csrf_exempt
def analytics(request):
    return render(request,'graphs.html',{})

@csrf_exempt
def analytic(request):
    return render(request,'analytics.html',{})
# @csrf_exempt
# def getFeedback(request):
#

